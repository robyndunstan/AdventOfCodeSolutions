package tools;

public class TestCase<T, U> {
	public int section;
	public T input;
	public U result;
	
	public TestCase() {}
	public TestCase(int section, T input, U result) {
		this.section = section;
		this.input = input;
		this.result = result;
	}
}