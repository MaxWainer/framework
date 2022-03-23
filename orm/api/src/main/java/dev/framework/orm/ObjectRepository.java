package dev.framework.orm;

import dev.framework.commons.repository.Repository;
import dev.framework.commons.repository.RepositoryObject;

public interface ObjectRepository<I, T extends RepositoryObject<I>> extends Repository<I, T> {}
