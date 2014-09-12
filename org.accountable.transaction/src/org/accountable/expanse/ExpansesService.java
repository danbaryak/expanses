package org.accountable.expanse;

import java.util.List;

public interface ExpansesService {
	public List<Expanse> getAllExpanses();
	public Expanse addExpanse(Expanse expanse);
	public void deleteExpanse(String uniqueId);
	public Expanse updateExpanse(Expanse expanse);
}
