package services.entites;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LivreSupp {
	private boolean estSupprimable;

	public LivreSupp() {
		super();
	}

	public LivreSupp(boolean estSupprimable) {
		super();
		this.estSupprimable = estSupprimable;
	}

	public boolean isEstSupprimable() {
		return estSupprimable;
	}

	public void setEstSupprimable(boolean estSupprimable) {
		this.estSupprimable = estSupprimable;
	}
}