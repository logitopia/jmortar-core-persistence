/**
 * Title: AddEntityParentModel.java
 *
 * Author: Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt; Date Created: 31-May-2016
 *
 * This code is the intellectual property of Logitopia Technologies.
 */
package com.logitopia.jmortar.core.persistence.dao.impl.fixtures;

import com.logitopia.jmortar.core.persistence.dao.DataAccessObject;
import com.logitopia.jmortar.core.persistence.dao.component.ReadableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.component.WritableDataAccessComponent;
import com.logitopia.jmortar.core.persistence.dao.impl.DataAccessObjectImpl;
import com.logitopia.jmortar.core.persistence.mock.ParentModel;
import com.logitopia.jmortar.core.persistence.mock.Shoe;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * The <tt>AddEntityParentModel</tt> class is an implementation that defines test data for parent model
 * <tt>addEntity</tt> testing.
 *
 * @author Stephen Cheesley &lt;stephen.cheesley@gmail.com&gt;
 */
public final class AddEntityParentModel {

  /**
   * Multi entry shoe ID.
   */
  public static final long[] MULTI_SHOE_ID = {4, 5, 6};
  
  /**
   * Multi entry shoe make.
   */
  public static final String[] MULTI_SHOE_MAKE = {"Four", "Five", "Six"};
  
  /**
   * The subject of the parent model test.
   */
  private DataAccessObjectImpl<ParentModel> subject;
  
  /**
   * The (mock) readable data access component for the test entity.
   */
  private ReadableDataAccessComponent<ParentModel> mockParentReadableDAC;

  /**
   * The (mock) writable data access component for the test entity.
   */
  private WritableDataAccessComponent<ParentModel> mockParentWritableDAC;
  
  /**
   * The first shoe DAO.
   */
  private DataAccessObject<Shoe> shoeOneDAO;
  
  /**
   * The second shoe DAO.
   */
  private DataAccessObject<Shoe> shoeTwoDAO;
  
  /**
   * The input data that will request the parent model to be created.
   */
  Map<String, Object> parentModelInputData;
  
  /**
   * Initialise the model resources.
   */
  public void initialise() {
    initParentModelInputData();
    initDAC();
    initShoeOneDAO();
    initShoeTwoDAO();
    
    initSubject();
  }
  
  private void initParentModelInputData() {
    parentModelInputData = new HashMap<>();
    
    Map<String, Object> shoeOne = new HashMap<>();
    shoeOne.put("shoeid", 4L);
    parentModelInputData.put("childOne", shoeOne);
    
    Map<String, Object> shoeTwo = new HashMap<>();
    shoeTwo.put("shoeid", 5L);
    parentModelInputData.put("childTwo", shoeTwo);
  }
  
  private void initSubject() {
    Map<String, DataAccessObject> children = new HashMap<>();
    children.put("childOne", shoeOneDAO);
    children.put("childTwo", shoeTwoDAO);
    
    subject = new DataAccessObjectImpl(children, mockParentReadableDAC, mockParentWritableDAC, ParentModel.class);
  }
  
  private void initDAC() {
    mockParentReadableDAC = mock(ReadableDataAccessComponent.class);
    mockParentWritableDAC = mock(WritableDataAccessComponent.class);
  }
  
  private void initShoeOneDAO() {
    shoeOneDAO = mock(DataAccessObject.class);
    
    Shoe resultShoe = new Shoe();
    resultShoe.setShoeid(MULTI_SHOE_ID[0]);
    resultShoe.setMake(MULTI_SHOE_MAKE[0]);
    when(shoeOneDAO.getEntity(any(Map.class))).thenReturn(resultShoe);
  }
  
  private void initShoeTwoDAO() {
    shoeTwoDAO = mock(DataAccessObject.class);
    
    Shoe resultShoe = new Shoe();
    resultShoe.setShoeid(MULTI_SHOE_ID[1]);
    resultShoe.setMake(MULTI_SHOE_MAKE[1]);
    when(shoeTwoDAO.getEntity(any(Map.class))).thenReturn(resultShoe);
  }

  public DataAccessObjectImpl<ParentModel> getSubject() {
    return subject;
  }

  public void setSubject(DataAccessObjectImpl<ParentModel> subject) {
    this.subject = subject;
  }

  public ReadableDataAccessComponent<ParentModel> getMockParentReadableDAC() {
    return mockParentReadableDAC;
  }

  public void setMockParentReadableDAC(ReadableDataAccessComponent<ParentModel> mockParentReadableDAC) {
    this.mockParentReadableDAC = mockParentReadableDAC;
  }

  public WritableDataAccessComponent<ParentModel> getMockParentWritableDAC() {
    return mockParentWritableDAC;
  }

  public void setMockParentWritableDAC(WritableDataAccessComponent<ParentModel> mockParentWritableDAC) {
    this.mockParentWritableDAC = mockParentWritableDAC;
  }

  public DataAccessObject<Shoe> getShoeOneDAO() {
    return shoeOneDAO;
  }

  public void setShoeOneDAO(DataAccessObject<Shoe> shoeOneDAO) {
    this.shoeOneDAO = shoeOneDAO;
  }

  public DataAccessObject<Shoe> getShoeTwoDAO() {
    return shoeTwoDAO;
  }

  public void setShoeTwoDAO(DataAccessObject<Shoe> shoeTwoDAO) {
    this.shoeTwoDAO = shoeTwoDAO;
  }

  public Map<String, Object> getParentModelInputData() {
    return parentModelInputData;
  }

  public void setParentModelInputData(Map<String, Object> parentModelInputData) {
    this.parentModelInputData = parentModelInputData;
  }
}
