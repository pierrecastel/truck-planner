package com.afklm.truckplanner.web.rest;
import com.afklm.truckplanner.domain.Delivery;
import com.afklm.truckplanner.repository.DeliveryRepository;
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
 * REST controller for managing Delivery.
 */
@RestController
@RequestMapping("/api")
public class DeliveryResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryResource.class);

    private static final String ENTITY_NAME = "delivery";

    private final DeliveryRepository deliveryRepository;

    public DeliveryResource(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    /**
     * POST  /deliveries : Create a new delivery.
     *
     * @param delivery the delivery to create
     * @return the ResponseEntity with status 201 (Created) and with body the new delivery, or with status 400 (Bad Request) if the delivery has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deliveries")
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) throws URISyntaxException {
        log.debug("REST request to save Delivery : {}", delivery);
        if (delivery.getId() != null) {
            throw new BadRequestAlertException("A new delivery cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Delivery result = deliveryRepository.save(delivery);
        return ResponseEntity.created(new URI("/api/deliveries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deliveries : Updates an existing delivery.
     *
     * @param delivery the delivery to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated delivery,
     * or with status 400 (Bad Request) if the delivery is not valid,
     * or with status 500 (Internal Server Error) if the delivery couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deliveries")
    public ResponseEntity<Delivery> updateDelivery(@RequestBody Delivery delivery) throws URISyntaxException {
        log.debug("REST request to update Delivery : {}", delivery);
        if (delivery.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Delivery result = deliveryRepository.save(delivery);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, delivery.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deliveries : get all the deliveries.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deliveries in body
     */
    @GetMapping("/deliveries")
    public List<Delivery> getAllDeliveries() {
        log.debug("REST request to get all Deliveries");
        return deliveryRepository.findAll();
    }

    /**
     * GET  /deliveries/:id : get the "id" delivery.
     *
     * @param id the id of the delivery to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the delivery, or with status 404 (Not Found)
     */
    @GetMapping("/deliveries/{id}")
    public ResponseEntity<Delivery> getDelivery(@PathVariable Long id) {
        log.debug("REST request to get Delivery : {}", id);
        Optional<Delivery> delivery = deliveryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(delivery);
    }

    /**
     * DELETE  /deliveries/:id : delete the "id" delivery.
     *
     * @param id the id of the delivery to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deliveries/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        log.debug("REST request to delete Delivery : {}", id);
        deliveryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
