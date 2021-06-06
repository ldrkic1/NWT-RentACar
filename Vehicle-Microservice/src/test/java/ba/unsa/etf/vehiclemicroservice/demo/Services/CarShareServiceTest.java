package ba.unsa.etf.vehiclemicroservice.demo.Services;
import ba.unsa.etf.vehiclemicroservice.demo.Exception.NotFoundException;
import ba.unsa.etf.vehiclemicroservice.demo.Model.CarShare;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarShareServiceTest {
    @Autowired
    CarShareService carShareService;
    @Test
    public void getAllCarShare() { assertTrue(carShareService.getAllCarShare().isEmpty());}
    @Test
    public void deleteCarShare() {
        assertAll(
                () -> assertThrows(
                        NotFoundException.class,
                        () -> carShareService.deleteById(10L)),
                () -> assertDoesNotThrow(() -> carShareService.deleteById(1L)),
                () -> assertTrue( carShareService.getAllCarShare().isEmpty())
        );
    }
    @Test
    public void createCarShare() {
        int freeSpaces = 3;
        CarShare carShare = new CarShare();
        carShare.setNumberOfFreeSpaces(freeSpaces);
        carShareService.createNewCarShare(carShare);
        assertEquals(1,carShareService.getAllCarShare().size());
    }
    @Test
    public void getCarShareById() {
        assertAll(
                () ->assertThrows(
                        NotFoundException.class,
                        () -> assertEquals(3,carShareService.getCarShareById(1L).getNumberOfFreeSpaces()))
        );
    }
}
