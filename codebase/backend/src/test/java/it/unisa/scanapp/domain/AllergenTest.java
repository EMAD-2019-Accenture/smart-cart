package it.unisa.scanapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.unisa.scanapp.web.rest.TestUtil;

public class AllergenTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Allergen.class);
        Allergen allergen1 = new Allergen();
        allergen1.setId(1L);
        Allergen allergen2 = new Allergen();
        allergen2.setId(allergen1.getId());
        assertThat(allergen1).isEqualTo(allergen2);
        allergen2.setId(2L);
        assertThat(allergen1).isNotEqualTo(allergen2);
        allergen1.setId(null);
        assertThat(allergen1).isNotEqualTo(allergen2);
    }
}
