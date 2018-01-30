package com.logitopia.jmortar.core.persistence.fixtures;

import com.logitopia.jmortar.core.persistence.annotation.Ignore;
import com.logitopia.jmortar.core.persistence.dao.annotation.HibernatePersistent;

/**
 * A mock model created to test reading annotated model.
 */
@Ignore
@HibernatePersistent(sessionFactory = "testSessionFactory")
public class MockPersistentModelSessionFactoryBean {
}
