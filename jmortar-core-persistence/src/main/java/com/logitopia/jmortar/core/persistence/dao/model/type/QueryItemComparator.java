/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.model.type;

/**
 * The <tt>QueryItemComparator</tt> defines the type of comparator used on a query item.
 *
 * @author Stephen Cheesley
 */
public enum QueryItemComparator {
  EQUALS,
  NEQUALS,
  MORE,
  LESS,
  MORE_EQ,
  LESS_EQ,
  LIKE;
}
