package it.aulab.springbootexample;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
// import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.context.SpringBootTest;

import it.aulab.springbootexample.model.Prodotto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


// @SpringBootTest
// serve a testare tutto il server
@DataJpaTest
// serve a testare solo quello che ha a che fare con la jpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringBootExampleApplicationTests {

	@Autowired
	private EntityManager entityManager;
	// gestisce la persistenza (modifica ->rimuovere aggiungere elementi al database) del database



	@Test
	void persistObjectProdotto() {
		Prodotto p= new Prodotto();

		p.setNome("Maglia Nike");
		p.setDescrizione("Maglia in cotone");
		p.setPrezzo(30F);

		entityManager.persist(p);
		// serve ad aggiungere l'elemento nel database
		TypedQuery<Prodotto> q =entityManager.createQuery("SELECT p FROM Prodotto p", Prodotto.class);

		List<Prodotto> all= q.getResultList();

		Assertions.assertThat(all).hasSize(3);
		// serve a testare se ho un numero determinato di elementi nel database
	}
	
	@Test
	void updateObjectProdotto() {
		TypedQuery<Prodotto> q =entityManager.createQuery("SELECT p FROM Prodotto p", Prodotto.class);

		List<Prodotto> all= q.getResultList();
		Prodotto p=all.get(0);
		
		String n = "nuovo nome";
		String d= "nuova descrizione";

		p.setDescrizione(d);
		entityManager.persist(p);


		List<Prodotto> allAfterPersist = q.getResultList();
		 p=allAfterPersist.get(0);

		 Assertions.assertThat(p).extracting("nome").isEqualTo(n);
		 Assertions.assertThat(p).extracting("descrizione").isEqualTo(d);
	}
}
