package services;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import design.Operation;
import design.Attribute;
import design.Class;
import design.Interface;


public class Services {
    
    /**
    * See http://help.eclipse.org/neon/index.jsp?topic=%2Forg.eclipse.sirius.doc%2Fdoc%2Findex.html&cp=24 for documentation on how to write service methods.
    */
    public EObject myService(EObject self, String arg) {
       // TODO Auto-generated code
      return self;
    }
    
    public String computeDefaultName(final EObject element) {
 		Predicate<EObject> predicate = null;
 		String name = element.getClass().getSimpleName();
 		name = name.substring(0, name.indexOf("Impl"));
 		predicate = new Predicate<EObject>() {
 			public boolean apply(EObject input) {
 				return input.getClass().getName().equals(element.getClass().getName());
 			}
 		};
 		if (element instanceof Class) {
 			name = "Class";
 		}else if (element instanceof Interface) {
 			name = "Interface";
 		}else if (element instanceof Operation) {
 			name = "operation";
 		}else if (element instanceof Attribute) {
 			name = "attribute";
 		}
 		
 		final List<EObject> existingElements = Lists.newArrayList(Iterables.filter(element.eContainer()
 				.eContents(), predicate));
 	
 		return name + existingElements.size();
 	}
}
