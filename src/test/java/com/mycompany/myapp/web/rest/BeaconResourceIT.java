package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.MuseuDashboardApp;
import com.mycompany.myapp.domain.Beacon;
import com.mycompany.myapp.repository.BeaconRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BeaconResource} REST controller.
 */
@SpringBootTest(classes = MuseuDashboardApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BeaconResourceIT {

    private static final String DEFAULT_CONTENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private BeaconRepository beaconRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBeaconMockMvc;

    private Beacon beacon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beacon createEntity(EntityManager em) {
        Beacon beacon = new Beacon()
            .contentName(DEFAULT_CONTENT_NAME)
            .contentType(DEFAULT_CONTENT_TYPE)
            .content(DEFAULT_CONTENT)
            .contentDescription(DEFAULT_CONTENT_DESCRIPTION);
        return beacon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beacon createUpdatedEntity(EntityManager em) {
        Beacon beacon = new Beacon()
            .contentName(UPDATED_CONTENT_NAME)
            .contentType(UPDATED_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .contentDescription(UPDATED_CONTENT_DESCRIPTION);
        return beacon;
    }

    @BeforeEach
    public void initTest() {
        beacon = createEntity(em);
    }

    @Test
    @Transactional
    public void createBeacon() throws Exception {
        int databaseSizeBeforeCreate = beaconRepository.findAll().size();

        // Create the Beacon
        restBeaconMockMvc.perform(post("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beacon)))
            .andExpect(status().isCreated());

        // Validate the Beacon in the database
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeCreate + 1);
        Beacon testBeacon = beaconList.get(beaconList.size() - 1);
        assertThat(testBeacon.getContentName()).isEqualTo(DEFAULT_CONTENT_NAME);
        assertThat(testBeacon.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testBeacon.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testBeacon.getContentDescription()).isEqualTo(DEFAULT_CONTENT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createBeaconWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beaconRepository.findAll().size();

        // Create the Beacon with an existing ID
        beacon.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeaconMockMvc.perform(post("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beacon)))
            .andExpect(status().isBadRequest());

        // Validate the Beacon in the database
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBeacons() throws Exception {
        // Initialize the database
        beaconRepository.saveAndFlush(beacon);

        // Get all the beaconList
        restBeaconMockMvc.perform(get("/api/beacons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beacon.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentName").value(hasItem(DEFAULT_CONTENT_NAME)))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].contentDescription").value(hasItem(DEFAULT_CONTENT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getBeacon() throws Exception {
        // Initialize the database
        beaconRepository.saveAndFlush(beacon);

        // Get the beacon
        restBeaconMockMvc.perform(get("/api/beacons/{id}", beacon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beacon.getId().intValue()))
            .andExpect(jsonPath("$.contentName").value(DEFAULT_CONTENT_NAME))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.contentDescription").value(DEFAULT_CONTENT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingBeacon() throws Exception {
        // Get the beacon
        restBeaconMockMvc.perform(get("/api/beacons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBeacon() throws Exception {
        // Initialize the database
        beaconRepository.saveAndFlush(beacon);

        int databaseSizeBeforeUpdate = beaconRepository.findAll().size();

        // Update the beacon
        Beacon updatedBeacon = beaconRepository.findById(beacon.getId()).get();
        // Disconnect from session so that the updates on updatedBeacon are not directly saved in db
        em.detach(updatedBeacon);
        updatedBeacon
            .contentName(UPDATED_CONTENT_NAME)
            .contentType(UPDATED_CONTENT_TYPE)
            .content(UPDATED_CONTENT)
            .contentDescription(UPDATED_CONTENT_DESCRIPTION);

        restBeaconMockMvc.perform(put("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeacon)))
            .andExpect(status().isOk());

        // Validate the Beacon in the database
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeUpdate);
        Beacon testBeacon = beaconList.get(beaconList.size() - 1);
        assertThat(testBeacon.getContentName()).isEqualTo(UPDATED_CONTENT_NAME);
        assertThat(testBeacon.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testBeacon.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testBeacon.getContentDescription()).isEqualTo(UPDATED_CONTENT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingBeacon() throws Exception {
        int databaseSizeBeforeUpdate = beaconRepository.findAll().size();

        // Create the Beacon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeaconMockMvc.perform(put("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beacon)))
            .andExpect(status().isBadRequest());

        // Validate the Beacon in the database
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBeacon() throws Exception {
        // Initialize the database
        beaconRepository.saveAndFlush(beacon);

        int databaseSizeBeforeDelete = beaconRepository.findAll().size();

        // Delete the beacon
        restBeaconMockMvc.perform(delete("/api/beacons/{id}", beacon.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
