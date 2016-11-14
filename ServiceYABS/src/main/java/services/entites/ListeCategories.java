package services.entites;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListeCategories {
	private List<String> categories;

	public ListeCategories() {
		super();
		categories = new ArrayList<String>();
	}

	public ListeCategories(List<String> categories) {
		super();
		this.categories = categories;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
}