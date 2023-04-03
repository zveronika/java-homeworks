package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Class ArrayIndexedCollection represents resizable array-backed collection of
 * objects. It extends class {@code Collection} and manages two private
 * variables - size, elements. Duplicate elements in this collection are allowed,
 * but storage of null references is not.
 *
 * @author Veronika Žunar
 *
 */
/**
 * Class ArrayIndexedCollection represents resizable array-backed collection of
 * objects. It implements interface {@code List} and manages two private
 * variables - size, elements.
 *
 * @author Veronika Žunar
 *
 */

public class ArrayIndexedCollection implements List {

    /**
     * Variable {@code size}, type int, represents number of elements actually
     * stored in {@code elements} array. In other words, it represents current size
     * of collection.
     *
     */
    private int size;

    /**
     * Variable {@code elements}, type array of objects, represents an array of
     * object references. It's length determines its current capacity (obviously, at
     * any time size can not be greater than array length).
     *
     */
    private Object[] elements;

    /**
     * Variable {@code modificationCount}, type int, represents number of
     * modifications of elements in the collection during the iteration.
     *
     */
    private long modificationCount;

    /**
     * Default constructor. Creates an instance of {@code ArrayIndexedCollection}
     * with capacity set to 16, which also means that constructor should preallocate
     * the elements array of that size.
     *
     */
    public static final int DEFAULT = 16;

    public ArrayIndexedCollection() {
        this(DEFAULT);
    }

    /**
     * Constructor which creates an instance of {@code ArrayIndexedCollection} with
     * the capacity set to value of a single integer parameter:
     * {@code initialCapacity}.
     *
     * @param initialCapacity initial size
     * @throws IllegalArgumentException if {@code initialCapacity} is less then 1
     */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException();

        this.size = 0;
        this.elements = new Object[initialCapacity];
        this.modificationCount = 0;
    }

    /**
     * Constructor. The elements of some other Collection are copied into this newly
     * constructed collection.
     *
     * @param collection a non-null reference to some other {@code Collection} which
     *                   elements are copied
     */
    public ArrayIndexedCollection(Collection collection) {
        this(collection, DEFAULT);
    }

    /**
     * * Constructor. The elements of some other Collection are copied into this
     * newly constructed collection. If the initialCapacity is smaller than the size
     * of the given collection, the size of the given collection should be used for
     * elements array preallocation.
     *
     * @param collection      a non-null reference to some other {@code Collection}
     *                        which elements are copied
     * @param initialCapacity initial size of new collection
     * @throws NullPointerException     if given {@code Collection} is {@code null}
     * @throws IllegalArgumentException if {@code initialCapacity} is less then 1
     */
    public ArrayIndexedCollection(Collection collection, int initialCapacity) {
        if (collection == null)
            throw new NullPointerException();

        if (initialCapacity < 1)
            throw new IllegalArgumentException();

        if (initialCapacity > collection.size()) {
            this.elements = new Object[initialCapacity];
        } else {
            this.elements = new Object[collection.size()];
        }
        this.addAll(collection);
        this.modificationCount = 0;
    }

    /**
     * Returns true if collection contains no objects and false otherwise.
     *
     * This method is implemented to determine result by utilizing method
     * {@code size()}.
     *
     * @return true if collection contains no objects, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the number of currently stored objects in this collections.
     *
     * @return number of currently stored objects in this collection
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Adds the given object into this collection (reference is added into first
     * empty place in the elements array). If the elements array is full, it should
     * be reallocated by doubling its size.
     *
     * This operation is done in O(n) complexity (O(n) average) where {@code n}
     * equals {@code this.size}.
     *
     * @param value to be added in this collection
     * @throws NullPointerException if Object value is null
     */
    @Override
    public void add(Object value) throws NullPointerException {
        if (value == null)
            throw new NullPointerException();

        if (this.elements.length == this.size)
            this.elements = Arrays.copyOf(this.elements, 2 * this.elements.length);

        this.elements[this.size] = value;
        this.size++;
        this.modificationCount++;
    }

    /**
     * Returns true only if the collection contains given value, as determined by
     * equals method. Given parameter can be {@code null}.
     *
     * @param value to be checked
     * @return true only if the collection contains given value
     */
    @Override
    public boolean contains(Object value) {
        if (value != null) {
            for (int i = 0; i < this.size; i++) {
                if (elements[i].equals(value))
                    return true;
            }
        }
        return false;
    }

    /**
     * Returns true only if the collection contains given value as determined by
     * equals method and removes one occurrence of it (in this class it is not
     * specified which one).
     *
     * @param value to be removed
     * @return true only if the collection contains given value as determined by
     *         equals method and removes one occurrence of it
     */
    @Override
    public boolean remove(Object value) {
        if (this.contains(value)) {
            int index = this.indexOf(value);
            this.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Allocates new array with size equals to the size of this collections, fills
     * it with collection content and returns the array. This method never returns
     * null.
     *
     * @return array of objects
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.elements, this.size);
    }

    /**
     * Method adds into the current collection all elements from the given
     * collection. This other collection remains unchanged.
     *
     * @param other collection to be added
     */
    @Override
    public void addAll(Collection other) {
        if (this.elements.length < this.size + other.size())
            this.elements = Arrays.copyOf(this.elements, this.size + other.size());

        Object[] otherArray = other.toArray();
        for (int i = 0; i < other.size(); i++)
            this.elements[i + this.size] = otherArray[i];

        this.size += other.size();
    }

    /**
     * Removes all elements from this collection.
     *
     * This operation is done in O(n) complexity (O(n) average) where {@code n}
     * equals {@code this.size}.
     */
    @Override
    public void clear() {
        for (int i = 0; i < this.size; i++)
            this.elements[i] = null;

        this.size = 0;
        this.modificationCount++;
    }

    /**
     * Returns the object that is stored in backing array at position index.
     *
     * This operation is done in O(1) complexity.
     *
     * @param index position
     * @throws IndexOutOfBoundsException if index is invalid (not between 0 and
     *                                   size-1)
     */
    public Object get(int index) {
        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException();

        return this.elements[index];
    }

    /**
     * Inserts does not overwrite the given value at the given position in array!
     * Method inserts value at the given position and elements at {@code position}
     * and at greater indexes must be shifted one place toward the end, so that an
     * empty place is created at {@code position}.
     *
     * This operation is done in O(n) complexity (O(n/2) average), unless the
     * underlying array has to be reallocated, in which case it is done in O(n + n)
     * complexity (O(n + n/2) average).
     *
     *
     * @param value    of an object to be inserted
     * @param position in array
     * @throws NullPointerException     if value equals null
     * @throws IllegalArgumentException if given position is not in legal range
     */
    public void insert(Object value, int position) {
        if (value == null)
            throw new NullPointerException();

        if (position < 0 || position > size)
            throw new IndexOutOfBoundsException();

        if (this.size == position) {
            this.elements[position] = value;
        } else {
            for (int i = this.size; i > position; i--) {
                this.elements[i] = this.elements[i - 1];
            }
            this.elements[position] = value;
        }
        this.size++;
        this.modificationCount++;

    }

    /**
     * Searches the collection and returns the index of the first occurrence of the
     * given value or -1 if the value is not found.
     *
     * This operation is done in O(n) complexity (O(n/2) average).
     *
     * @param value of object to be searched
     */
    public int indexOf(Object value) {
        int index = -1;
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(value)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Removes element at specified index from collection. Element that was
     * previously at location {@code index+1} after this operation is on location
     * {@code index}, etc. Legal indexes are 0 to {@code size-1}.
     *
     * This operation is done in O(n) complexity (O(n) average).
     *
     * @param index of object to be removed
     * @throws IndexOutOfBoundsException if {@code index} is invalid
     */
    public void remove(int index) {
        if (index < 0 || index > this.size - 1)
            throw new IndexOutOfBoundsException();

        this.elements[index] = null;
        for (int i = index; i < this.size; i++)
            this.elements[i] = this.elements[i + 1];

        this.size--;
        this.modificationCount++;
    }

    /**
     *
     * @author Veronika Žunar
     *
     */

    private static class ArrayElementsGetter implements ElementsGetter {

        /**
         *
         */
        private int index;

        /**
         *
         */
        private ArrayIndexedCollection coll;

        /**
         *
         */
        private long savedModificationCount;

        /**
         * Constructor that creates an instance of {@code ArrayIndexedCollection}
         * iterator thats called {@code ArrayElementsGetter}. It takes one parameter -
         * {@code ArrayIndexedCollection collection} whose elements are about to bes
         * iterated.
         *
         * @param collection to be iterated
         */
        public ArrayElementsGetter(ArrayIndexedCollection collection) {
            this.index = 0;
            this.coll = collection;
            this.savedModificationCount = collection.modificationCount;
        }

        /**
         * This method checks if element is followed by another element. Using this
         * method it can be checked whether the iterator reached the end of array.
         *
         * @return true if element has next one, false otherwise
         */
        @Override
        public boolean hasNextElement() {
            if (savedModificationCount != coll.modificationCount)
                throw new ConcurrentModificationException();

            return (index < coll.size());
        }

        /**
         * This method gets the next element of array collection. Returning an element
         * is successful only if the collection hasn't been modified since the creation
         * of {@code ArrayElementsGetter}.
         *
         * @return next element of collection
         */
        @Override
        public Object getNextElement() {
            Object value = new Object();
            if (this.hasNextElement()) {
                value = coll.get(index);
                index++;
            } else {
                throw new NoSuchElementException();
            }
            return value;
        }
    }

    /**
     * Creates new instance of ElementsGetter for iteration trough array collection.
     */
    @Override
    public ElementsGetter createElementsGetter() {
        return new ArrayElementsGetter(this);
    }
}
