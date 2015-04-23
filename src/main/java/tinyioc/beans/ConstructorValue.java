package tinyioc.beans;

/**
 * Created by yongjie on 15-4-23.
 */
public class ConstructorValue {
	String index;
	String value;
	String ref;
	String type;

	public ConstructorValue(String index, String value, String ref, String type) {
		this.index = index;
		this.value = value;
		this.ref = ref;
		this.type = type;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
