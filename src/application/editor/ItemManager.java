package application.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import component.components.Identifiable;
import myUtil.StringGenerator;
import myUtil.Utility;

/**
 * Map wrapper for String-T pairs for objects of type T that can be identified
 * by a String ID. Additionally supports automatic ID generation.
 *
 * @param <T> the type of the values stored
 *
 * @author Alex Mandelias
 *
 * @see Identifiable
 * @see StringGenerator
 */
class ItemManager<T extends Identifiable<String>> {

	private final Map<String, T>               map;
	private final Map<String, StringGenerator> idGenerators;

	/** Constructs the ItemManager */
	public ItemManager() {
		map = new HashMap<>();
		idGenerators = new HashMap<>();
	}

	/**
	 * Adds an {@code Item} to the Manager.
	 *
	 * @param item the Item
	 *
	 * @throws NullPointerException if {@code item == null}
	 * @throws DuplicateIdException if an Item with the same ID already exists in
	 *                              this Manager
	 */
	public void add(T item) {
		final String id = item.getID();
		if (map.containsKey(id))
			throw new DuplicateIdException(id);

		map.put(id, item);
	}

	/**
	 * Removes an {@code Item} from the Manager.
	 *
	 * @param item the Item
	 *
	 * @throws NullPointerException if {@code item == null}
	 */
	public void remove(T item) {
		map.remove(item.getID());
	}

	/**
	 * Returns the {@code Item} with the given ID.
	 *
	 * @param id the ID
	 *
	 * @return the Item with that ID
	 *
	 * @throws MissingComponentException if no Item with the ID exists
	 */
	public T get(String id) throws MissingComponentException {
		final T item = map.get(id);
		if (item == null)
			throw new MissingComponentException(id);

		return item;
	}

	/**
	 * Returns the number of {@code Items} in this Manager.
	 *
	 * @return the number of items
	 */
	public int size() {
		return map.size();
	}

	/**
	 * Returns every {@code Item} in this Manager.
	 * <p>
	 * <b>Note</b> that this does <i>not</i> return a copy of the items. Any changes
	 * to the Items will be reflected in this ItemManager object.
	 *
	 * @return a List with the Items
	 */
	public List<T> getall() {
		return getall(c -> true);
	}

	/**
	 * Returns the Items for which the {@code predicate} evaluates to {@code true}.
	 * <p>
	 * <b>Note</b> that this does <i>not</i> return a copy of the items. Any changes
	 * to the Items will be reflected in this ItemManager object.
	 *
	 * @param predicate the Predicate that will be evaluated on each Item
	 *
	 * @return a List with the Items
	 */
	public List<T> getall(Predicate<T> predicate) {
		final List<T> list = new ArrayList<>(size());
		Utility.foreach(map.values(), item -> {
			if (predicate.test(item))
				list.add(item);
		});
		return list;
	}

	/**
	 * Returns the next ID generated by the specified Generator.
	 *
	 * @param generatorID the target Generator
	 *
	 * @return the next ID of that Generator
	 *
	 * @throws NullPointerException if the generatorID doesn't correspond to an
	 *                              existing Generator
	 */
	public String getNextID(String generatorID) {
		return idGenerators.get(generatorID).get();
	}

	/**
	 * Creates a Generator.
	 *
	 * @param generatorID the Generator's ID
	 * @param text        the text that will be formatted
	 */
	public void addGenerator(String generatorID, String text) {
		addGenerator(generatorID, text, 0);
	}

	/**
	 * Creates a generator.
	 *
	 * @param generatorID the Generator's ID
	 * @param text        the text that will be formatted
	 * @param start       the initial counter value
	 */
	public void addGenerator(String generatorID, String text, int start) {
		idGenerators.put(generatorID, new StringGenerator(text, start));
	}

	/**
	 * Thrown when another Item is already associated with an {@code ID}.
	 *
	 * @author Alex Mandelias
	 */
	public static class DuplicateIdException extends RuntimeException {

		/**
		 * Constructs the Exception with an {@code ID}.
		 *
		 * @param id the duplicate id
		 */
		public DuplicateIdException(String id) {
			super(String.format("Another Item associated with ID %s", id)); //$NON-NLS-1$
		}
	}
}
