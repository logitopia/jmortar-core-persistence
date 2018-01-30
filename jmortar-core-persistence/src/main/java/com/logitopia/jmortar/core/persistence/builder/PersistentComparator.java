package com.logitopia.jmortar.core.persistence.builder;

import com.logitopia.jmortar.core.persistence.annotation.PersistentChild;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * A <tt>Comparator</tt> that compares two models to see if they are parents or not.
 */
public class PersistentComparator implements Comparator<Class> {

    /**
     * Check to see if the given model has an instance of <tt>PersistentChild</tt> on any of the fields
     * (i.e. is a parent model).
     * @param modelClass The class of the model to test.
     * @return The flag indicating whether or not the model is a parent model (true==parent).
     */
    private boolean isModelParent(final Class modelClass) {
        for(Field field : modelClass.getDeclaredFields()){
            for (Annotation annotation:field.getDeclaredAnnotations()) {
                // Check to see if it is the PersistentChild
                if (annotation.annotationType() == PersistentChild.class) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     * @param o1 The first object to compare.
     * @param o2 The second object to compare.
     * @return
     */
    @Override
    public int compare(Class o1, Class o2) {
        int result = 0;

        boolean b1 = isModelParent(o1);
        boolean b2 = isModelParent(o2);

        // Truth Table
        if (!b1 && b2) result = -1; // Less than if o2 is a child (child one goes lower than one without)
        if (b1 && !b2) result = 1;

        return result;
    }
}
