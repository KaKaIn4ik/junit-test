import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryTests {

    @Test
    @DisplayName("Расстояние меньше 2 км.")
    void distanceLessTwoKMTest() {
        Delivery delivery = new Delivery(1, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);

        assertEquals(400, delivery.calculateDeliveryCost(), "Ожидалось значение 400");
    }

    @Test
    @DisplayName("Не достиг минимальной суммы")
    void distanceFromTwoToTen() {
        Delivery delivery = new Delivery(5, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);

        assertNotEquals(150, delivery.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Расстояние от 10 до 30 км")
    void distanceFromTenToThirty() {
        Delivery delivery = new Delivery(20, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);

        assertNotEquals(300, delivery.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Расстояние больше 30 км")
    void distanceMoreThanThirty() {
        Delivery delivery = new Delivery(40, CargoDimension.SMALL, false, ServiceWorkload.NORMAL);

        assertNotEquals(500, delivery.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Размер груза большой")
    void cargoLargeTest() {
        Delivery delivery = new Delivery(5, CargoDimension.LARGE, false, ServiceWorkload.NORMAL);

        assertNotEquals(250, delivery.calculateDeliveryCost());
    }

    @Test
    @DisplayName("Проверка на предмет исключения при доставке хрупких грузов")
    void fragileCargoDeliveryExceptionTest() {
        Delivery delivery = new Delivery(40, CargoDimension.LARGE, true, ServiceWorkload.NORMAL);

        // Проверяем, что метод calculateDeliveryCost выбрасывает исключение
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            delivery.calculateDeliveryCost();
        });

        assertEquals("Fragile cargo cannot be delivered for the distance more than 30", exception.getMessage());
    }

    @Test
    @DisplayName("Некорректная дистанция")
    void incorrectDistance() {
        Delivery delivery = new Delivery(-5, CargoDimension.LARGE, false, ServiceWorkload.NORMAL);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            delivery.calculateDeliveryCost();
        });

        assertEquals("destinationDistance should be a positive number!", exception.getMessage());
    }

    @Test
    @DisplayName("Некорректная загруженость")
    void incorrectWorkload() {
        Delivery delivery = new Delivery(20, CargoDimension.SMALL, false, null);

        Exception exception = assertThrows(NullPointerException.class, () -> {
            delivery.calculateDeliveryCost();
        });

        assertEquals("Cannot invoke \"ServiceWorkload.getDeliveryRate()\" because \"this.deliveryServiceWorkload\" is null", exception.getMessage());
    }
}
