package exercise01;

import static org.junit.Assert.*;

import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.StringContains;
import org.hamcrest.core.StringStartsWith;
import org.hamcrest.number.IsCloseTo;
import org.hamcrest.number.OrderingComparison;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.either;

public class PersonTest {
	Person myAnonPerson;
	Person myPerson;
	Cat cat1;
	Cat cat2;
	Cat cat3;
	Cat cat4;
	Cat mockCat;
	
	@Before
	public void setUp() throws Exception {
		this.myAnonPerson = new Person();
		this.myPerson 	  = new Person("my person", 200, 1);
		this.cat1 = new Cat("My cat of her name Foo", 100);
		this.cat2 = new Cat("my cat of his name Bar", 100);
		this.cat3 = new Cat("my cat of her name Baz", 100);
		this.cat4 = new Cat("my cat of his name Qux", 100);
		this.mockCat = Mockito.mock(Cat.class);
	}
	
	@Test
	public void testPersonAddCat() {
		myPerson.addCat(cat1);
		myPerson.addCat(cat2);
		myPerson.addCat(cat3);
		assertEquals(cat1, myPerson.getCat(0));
	}
	
	@Test(expected=NullPointerException.class)
	public void testPersonAddCatNull() {
		myAnonPerson.addCat(cat1);
		myAnonPerson.addCat(cat2);
		myAnonPerson.addCat(cat3);
		assertEquals(cat1, myAnonPerson.getCat(0));
	}
	
	@Test(expected=TooManyCatException.class)
	public void testPersonAddCatTooMuch() {
		myPerson.addCat(cat1);
		myPerson.addCat(cat2);
		myPerson.addCat(cat3);
		myPerson.addCat(cat4);
		assertEquals(cat1, myPerson.getCat(0));
	}
	
	/**
	 * java.lang.AssertionError: 
	 * Expected: (is "dadada")
     * but: was <Cat [name=my cat, weight=100]>
	 */
	@Test
	public void testHamcrestMatcher() {
		myPerson.addCat(cat1);
		myPerson.addCat(cat2);
		myPerson.addCat(cat3);
		assertThat(myPerson.getCat(1), anyOf(Is.is(cat1), Is.is(cat2), Is.is(cat3)));
		assertThat(myPerson.getCat(1), either(Is.is(cat1)).or(Is.is(cat2)).or(Is.is(cat3)));
	}
	
	/**
	 * <p>
	 * Expected: (a string containing "Foo" and a string starting with "[name")
     * but: a string starting with "[name" was "Cat [name=My cat of her name Foo, weight=100]"
	 * </p>
	 */
	@Test
	public void testAddASingleCat() {
		myPerson.addCat(cat1);
		assertThat(myPerson.getCat(0).toString(), allOf(StringContains.containsString("Foo"), StringStartsWith.startsWith("Cat [name")));
	}
	
	/**
	 * Expected: a numeric value within <1.0E-6> of <12.344498>
     * but: <12.345> differed by <5.010000000008906E-4>
	 */
	@Test
	public void testDouble() {
		assertThat(12.345d, IsCloseTo.closeTo(12.344498, 0.01));
	}
	
	/**
	 * java.lang.AssertionError: Hey,
	 * Expected: a value greater than <2>
     * but: <1> was less than <2>
	 */
	@Test
	public void testGreater() {
		assertThat("Hey, ", 1, OrderingComparison.greaterThan(0));
	}
	
	/**
	 * Expected: [<Cat [name=my cat of her name Baz, weight=100]>, <Cat [name=My cat of her name Foo, weight=100]>] in any order
     * but: Not matched: <Cat [name=my cat of his name Qux, weight=100]>
	 */
	@Test
	public void testCatArray() {
		assertThat(new Cat[]{cat1, cat3}, IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(new Cat[]{cat3, cat1}));//equalTo("bar"), equalTo("foo")
		assertThat(new Cat[]{cat1, cat3}, IsArrayContainingInAnyOrder.arrayContainingInAnyOrder(IsEqual.equalTo(cat3), IsEqual.equalTo(cat1)));
	}
	
	@Test
	public void testMockito() {
		Mockito.when(mockCat.toString()).thenReturn("is not a Cat.");
		assertThat(mockCat.toString(), Is.is("is not a Cat."));
	}
}
