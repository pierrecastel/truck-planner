package com.afklm.truckplanner.web.rest;
import com.afklm.truckplanner.domain.TruckCompany;
import com.afklm.truckplanner.repository.TruckCompanyRepository;
import com.afklm.truckplanner.web.rest.errors.BadRequestAlertException;
import com.afklm.truckplanner.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TruckCompany.
 */
@RestController
@RequestMapping("/api")
public class TruckCompanyResource {

    private final Logger log = LoggerFactory.getLogger(TruckCompanyResource.class);

    private static final String ENTITY_NAME = "truckCompany";

    private final TruckCompanyRepository truckCompanyRepository;

    public TruckCompanyResource(TruckCompanyRepository truckCompanyRepository) {
        this.truckCompanyRepository = truckCompanyRepository;
    }

    /**
     * POST  /truck-companies : Create a new truckCompany.
     *
     * @param truckCompany the truckCompany to create
     * @return the ResponseEntity with status 201 (Created) and with body the new truckCompany, or with status 400 (Bad Request) if the truckCompany has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/truck-companies")
    public ResponseEntity<TruckCompany> createTruckCompany(@RequestBody TruckCompany truckCompany) throws URISyntaxException {
        log.debug("REST request to save TruckCompany : {}", truckCompany);
        if (truckCompany.getId() != null) {
            throw new BadRequestAlertException("A new truckCompany cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TruckCompany result = truckCompanyRepository.save(truckCompany);
        return ResponseEntity.created(new URI("/api/truck-companies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /truck-companies : Updates an existing truckCompany.
     *
     * @param truckCompany the truckCompany to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated truckCompany,
     * or with status 400 (Bad Request) if the truckCompany is not valid,
     * or with status 500 (Internal Server Error) if the truckCompany couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/truck-companies")
    public ResponseEntity<TruckCompany> updateTruckCompany(@RequestBody TruckCompany truckCompany) throws URISyntaxException {
        log.debug("REST request to update TruckCompany : {}", truckCompany);
        if (truckCompany.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TruckCompany result = truckCompanyRepository.save(truckCompany);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, truckCompany.getId().toString()))
            .body(result);
    }

    /**
     * GET  /truck-companies : get all the truckCompanies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of truckCompanies in body
     */
    @GetMapping("/truck-companies")
    public List<TruckCompany> getAllTruckCompanies() {
        log.debug("REST request to get all TruckCompanies");
        return truckCompanyRepository.findAll();
    }

    /**
     * GET  /truck-companies/:id : get the "id" truckCompany.
     *
     * @param id the id of the truckCompany to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the truckCompany, or with status 404 (Not Found)
     */
    @GetMapping("/truck-companies/{id}")
    public ResponseEntity<TruckCompany> getTruckCompany(@PathVariable Long id) {
        log.debug("REST request to get TruckCompany : {}", id);
        Optional<TruckCompany> truckCompany = truckCompanyRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(truckCompany);
    }

    /**
     * DELETE  /truck-companies/:id : delete the "id" truckCompany.
     *
     * @param id the id of the truckCompany to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/truck-companies/{id}")
    public ResponseEntity<Void> deleteTruckCompany(@PathVariable Long id) {
        log.debug("REST request to delete TruckCompany : {}", id);
        truckCompanyRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
