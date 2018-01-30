/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A field on the parent that requires a child DAO.
 * 
 * @author Stephen Cheesley
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PersistentChild {
}
