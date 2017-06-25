package dt.egf.utils.workflow;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egf.core.producer.InvocationException;
import org.eclipse.egf.ftask.producer.context.ITaskProductionContext;
import org.eclipse.egf.ftask.producer.invocation.ITaskProduction;
import org.eclipse.egf.model.fcore.Contract;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.mwe.core.WorkflowRunner;
import org.eclipse.emf.mwe.core.resources.ResourceLoaderFactory;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MweWorkflowRunner implements ITaskProduction {

	public static final String KEY_MWE = "mwe";
	public static final String KEY_BUNDLEROOT = "mweBundleRoot";
	public static final String KEY_WORKSPACEROOT = "workspaceRoot";
	private URI mwe = null;

	@Override
	public void preExecute(ITaskProductionContext productionContext,
			IProgressMonitor monitor) throws InvocationException {

		/* 
		 * Mandatory contract
		 * Format can be platform path, e.g. <project>/<projectfilepath-to-mwe>
		 * or platform URI, e.g. platform:/plugin/<platform path>
		 */
		mwe = ContractHelper.getInputURI(productionContext, KEY_MWE);
	}

	@Override
	public void doExecute(ITaskProductionContext productionContext,
			IProgressMonitor monitor) throws InvocationException {
		
	    boolean success = false;

		if (mwe != null) {
			// prefer local mwe flow when there is one
			mwe = URIUtils.getSchemeURI(mwe);
			URI use_mwe = URIUtils.getWeakestResolvablePlatformURI(mwe);
//			logger.logInfo("-", 0);
//			logger.logInfo("Start MweWorkflowRunner with mwe : " + use_mwe, 0);
			
			URIConstructor uc = new URIConstructor();
			uc.parseURI(use_mwe, true);
			//String pathSep = URIConstructor.getPathSep();
			String workspaceRoot = (String)uc.getValue(KeysURI.KEY_WORKSPACEROOTPATH); 
			// allow overrule by specific contract
			if (ContractHelper.hasInputContract(productionContext, KEY_WORKSPACEROOT)) {
				String contract_workspaceRoot = productionContext.getInputValue(KEY_WORKSPACEROOT, String.class);
				if (contract_workspaceRoot != null) {
					workspaceRoot = contract_workspaceRoot;
				}
			}

			String mweBundleName = (String)uc.getValue(KeysURI.KEY_PROJECTNAME);
//			logger.logTrace("MweWorkflowRunner: mweBundleName = " + mweBundleName, 2);
			String mweBundlePath = (String)uc.getValue(KeysURI.KEY_PROJECTFILEPATH); 
	//		logger.logTrace("MweWorkflowRunner: mweBundlePath = " + mweBundlePath, 2);
			String mweBundleRoot = (String)uc.getValue(KeysURI.KEY_PROJECTPATH);
		//	logger.logTrace("MweWorkflowRunner: mweBundleRoot = " + mweBundleRoot, 2);
			// allow overrule by specific contract
			if (ContractHelper.hasInputContract(productionContext, KEY_BUNDLEROOT)) {
				String contract_mweBundleRoot = productionContext.getInputValue(KEY_BUNDLEROOT, String.class);
				if (contract_mweBundleRoot != null) {
					mweBundleRoot = contract_mweBundleRoot;
			//		logger.logTrace("MweWorkflowRunner: mweBundleRoot = " + mweBundleRoot, 2);
				}
			}
			
			String mwePath = (String)uc.getValue(KeysURI.KEY_FILESTRING); // Portable format
		//	logger.logTrace("MweWorkflowRunner: mwePath = " + mwePath, 2);
			/* Need absolute file path to mwe:
			 * e.g. C:/Work/Tools/LunaSP1/ws/runtime-LACE-test1/asml.lace.generator/src/asml/lace/generator/workflow/DDFGenerator.mwe
			 * so without file: protocol prefix
			 */
			ArrayList<String> arguments = new ArrayList<String>();
			arguments.add(mwePath);
			arguments.add("-p" + KEY_BUNDLEROOT + "=" + mweBundleRoot);
			arguments.add("-p" + KEY_WORKSPACEROOT + "=" + workspaceRoot);
			
			// all other contracts are treated like property settings:
			for (Contract contract : productionContext.getInputValueKeys()) {
				String name = contract.getName();
				if (name.equals(KEY_MWE)) continue;  // not needed as property
				if (name.equals(KEY_BUNDLEROOT)) continue; // was calculated above from others
				if (name.equals(KEY_WORKSPACEROOT)) continue; // was calculated above from others
				String value = productionContext.getInputValue(contract.getName(), String.class);
				
				//logger.logTrace("MweWorkflowRunner: "+ name + " = " + value, 2);

				arguments.add("-p" + contract.getName() + "=" + value);
			}
			
			//arguments to array
			String[] args = new String[arguments.size()];
			args = arguments.toArray(args);
			
			//**
			ClassLoader old_class_loader = Thread.currentThread().getContextClassLoader();
			String old_resourceLoader = System.getProperty(ResourceLoaderFactory.PARAM_RESOURCELOADER_CLASS);
			
		//	logger.logTrace("Starting WorkflowRunner with the following arguments:", 0);
			for (String arg: args) {
		//		logger.logTrace(arg, 2);
			}
			try {
				//**
//				Thread.currentThread().setContextClassLoader(MyLog.class.getClassLoader());
				/* 
				 * It seems that we have to use the class loader associated with the WorkflowRunner.
				 * Using the MyLog class loader also works to get logging to work, but it will then
				 * not have the class path available that is defined by the mwe bundle from
				 * which we're running. 
				 */
				ClassLoader wfClassLoader = WorkflowRunner.class.getClassLoader();
				ClassLoader new_class_loader = wfClassLoader;
				
				IProject project = null;
				try {
					/*
					 * When the template project is in the local workspace, we want to load templates from
					 * local project.
					 * The default reader will always load from installed bundle first, so we need to override
					 * with a specific reader implementation that allows to read local workspace project first.
					 */
					project = ResourcesPlugin.getWorkspace().getRoot().getProject(mweBundleName);
					if (project != null) {
						// templates to be found in workspace local bundle project:
						// register a resource loader to be used (see ResourceLoaderFactory).
						// The overriding loader requires some configuration. Since the loader needs to be constructed
						// with default constructor, configurations is done with the same mechanism of system properties.
						URL templatesBundleURL = new URL("file:/" + mweBundleRoot);
						System.setProperty(ResourceLoaderFactory.PARAM_RESOURCELOADER_CLASS, MweWSOverridingResourceLoader.class.getName());
						System.setProperty(MweWSOverridingResourceLoader.OVERRIDING_BUNDLE_NAME, mweBundleName);
						System.setProperty(MweWSOverridingResourceLoader.OVERRIDING_BUNDLE_ROOT, templatesBundleURL.toString());
					}
				} catch (Exception e) {
					// do nothing: use default bundle loader.
				}
				// Workaround for "xcore.lang" package read exception
				//FixPackageReadException.fixXcore(null);
				
				Thread.currentThread().setContextClassLoader(new_class_loader);
				//MyLog.registerToLogFactory();

				// TODO : Configure logging. WorkflowRunner uses apache logger.
//				WorkflowRunner.main(args);

                OutputStreamCombiner outputStreamCombiner = new OutputStreamCombiner(Arrays.asList(System.out, new ByteArrayOutputStream())); 
                PrintStream consoleMessages = new PrintStream(outputStreamCombiner);
                System.setOut(consoleMessages);

                WorkflowRunner runner = new WorkflowRunner();
                CommandLine line = getCommandLine(args);
                success = runner.doRun(line, args);

				if(false == success) {
				    throw new MweException(": Errors during the execution of the MWE engine");
				}

				//logger.logTrace("WorkflowRunner status: " + success);

			} catch (Exception e) {

                handle_exception(Severity.FATAL, e.getMessage() != null ? e.getMessage() : e.getClass().getCanonicalName(), null, e, monitor);
			}
			
			//**
			finally {
			    if(true == success) {
				// restore loaders
				if (old_resourceLoader == null) {
					System.clearProperty(ResourceLoaderFactory.PARAM_RESOURCELOADER_CLASS);
				} else {
					System.setProperty(ResourceLoaderFactory.PARAM_RESOURCELOADER_CLASS, old_resourceLoader);
				}
				Thread.currentThread().setContextClassLoader(old_class_loader);
				//MyLog.unregisterFromLogFactory();
               // logger.logInfo("Finished WorkflowRunner", 0);
			    }
			    else {
			        monitor.setCanceled(true);
			    }
			}
		}
	}

	@Override
	public void postExecute(ITaskProductionContext productionContext,
			IProgressMonitor monitor) throws InvocationException {
		// TODO Auto-generated method stub

	}


	private static class OutputStreamCombiner extends OutputStream {

        private List<OutputStream> outputStreams;

        public OutputStreamCombiner(List<OutputStream> outputStreams) {
            this.outputStreams = outputStreams;
        }

        public void write(int b) throws IOException {
            for (OutputStream os : outputStreams) {
                os.write(b);
            }
        }

        public void flush() throws IOException {
            for (OutputStream os : outputStreams) {
                os.flush();
            }
        }

        public void close() throws IOException {
            for (OutputStream os : outputStreams) {
                os.close();
            }
        }
    }

	    private static final String PARAM = "p";
	    private static final String MONITOR = "m";
	    private static final String ANT = "a";
	    private static final String CMDL = "c";
	    private static final String ENGINE = "e";

	    @SuppressWarnings("static-access")
	    protected static CommandLine getCommandLine(String[] args) throws org.apache.commons.cli.ParseException {
	        final Options options = new Options();

	        options.addOption(OptionBuilder
	                .hasArgs()
	                .withArgName("className,moreArgs")
	                .withDescription(
	                        "the name of a class that implements ProgressMonitor. More arguments can be appended that will be injected to the monitor,"
	                                + " if it has a init(String[] args) method.").withLongOpt("monitorClass")
	                .withValueSeparator(',').create(MONITOR));

	        options.addOption(OptionBuilder.withLongOpt("ant").withDescription("must be set when using in Ant context")
	                .create(ANT));

	        final Option paramOption = OptionBuilder.withArgName("key=value")
	                .withDescription("external property that is handled as workflow property").hasArgs().create(PARAM);
	        paramOption.setLongOpt("param");
	        options.addOption(paramOption);

	        options.addOption(OptionBuilder
	                .hasArgs()
	                .withArgName("className")
	                .withDescription(
	                        "the name of a class that implements a public method 'public void processCmdLine(String[] cmdLineArgs, Map paramsToUseInWorkflow, WorkflowContext ctx)'.")
	                .withLongOpt("cmdLineProcessor").create(CMDL));

	        final Option runnerOption = OptionBuilder.withArgName("className")
	                .withDescription("WorkflowRunnerEngine class").hasArgs().create(ENGINE);
	        runnerOption.setLongOpt("engine");
	        options.addOption(runnerOption);

	        // create the parser
	        final CommandLineParser parser = new PosixParser();
	        CommandLine line = parser.parse(options, args);
	        return line;
	    }

	    private enum Severity{
	        NEVER,
	        OK,
	        INFO,
	        DEBUG,
	        WARNING,
	        ERROR,
	        FATAL,

	    };
	    private static String exceptionLevel;
	    private static String interruptAtExceptionLevel;
	    
	    private void handle_exception(Severity severity, String message1, String message2, Throwable e, IProgressMonitor monitor) throws MweException{
	      //  logger.logInfo(severity + message1);
	        if(message2 != null)
	        //    logger.logInfo(severity + message2);
	        update_exceptionLevel(severity.toString());
	        if (consider_stop_sequence(monitor)) {
	            throw new MweException(message1, e);
	        };
	    }

	    private void update_exceptionLevel(String newExceptionLevel){
	        Severity currentSeverity = Severity.valueOf(exceptionLevel);
	        Severity newSeverity = Severity.valueOf(newExceptionLevel);

	        if(currentSeverity.compareTo(newSeverity) < 0)
	            exceptionLevel = new String(newExceptionLevel); 
	    }

	    private boolean consider_stop_sequence(IProgressMonitor monitor){
	        Severity exceptionLevelSeverity = Severity.valueOf(exceptionLevel);
	        Severity interruptAtExceptionLevelSeverity = Severity.valueOf(interruptAtExceptionLevel);

	        if(exceptionLevelSeverity.compareTo(interruptAtExceptionLevelSeverity) >= 0) {
	            //monitor.setCanceled(true);
	            return true;
	        }
	        return false;
	    }

}