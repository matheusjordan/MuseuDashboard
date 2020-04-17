package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BeaconTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beacon.class);
        Beacon beacon1 = new Beacon();
        beacon1.setId(1L);
        Beacon beacon2 = new Beacon();
        beacon2.setId(beacon1.getId());
        assertThat(beacon1).isEqualTo(beacon2);
        beacon2.setId(2L);
        assertThat(beacon1).isNotEqualTo(beacon2);
        beacon1.setId(null);
        assertThat(beacon1).isNotEqualTo(beacon2);
    }
}
