package guru.sfg.beer.inventory.service.services;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import brewery.model.events.NewInventoryEvent;

import javax.jms.JMSException;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewInventoryListener {
    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void Listen(NewInventoryEvent event) throws JMSException {
        log.debug("Got Inventory :" + event.toString());

        beerInventoryRepository.save(BeerInventory.builder()
                        .beerId(event.getBeerDto().getId())
                        .upc(event.getBeerDto().getUpc())
                        .quantityOnHand(event.getBeerDto().getQuantityOnHand())
                        .build());
    }
}
