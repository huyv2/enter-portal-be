package hr.ewallet.adapter.out.database.repository;

import java.lang.reflect.Parameter;

import hr.ewallet.application.port.out.store.HistoryStorePort;

public class HistoryStoreRepository<T> implements HistoryStorePort<T> {

	@Override
	public boolean save(Parameter... parameter) {
		return false;
	}

	@Override
	public T inquiry() {
		return null;
	}

}
