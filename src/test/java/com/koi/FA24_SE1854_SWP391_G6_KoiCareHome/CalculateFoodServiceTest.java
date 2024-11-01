package com.koi.FA24_SE1854_SWP391_G6_KoiCareHome;

import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.model.Fish;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.repository.FishRepository;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.service.CalculateFoodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CalculateFoodServiceTest {

    private FishRepository fishRepository;
    private CalculateFoodService calculateFoodService;

    @BeforeEach
    public void setUp() {
        fishRepository = mock(FishRepository.class);
        calculateFoodService = new CalculateFoodService(fishRepository);
    }

    @Test
    public void testCalculateFoodBaseOnWeights() {
        Fish fish1 = new Fish();
        fish1.setWeight(BigDecimal.valueOf(2.0)); // 2 kg
        Fish fish2 = new Fish();
        fish2.setWeight(BigDecimal.valueOf(3.0)); // 3 kg

        when(fishRepository.findAllFishWithPondId(1)).thenReturn(Arrays.asList(fish1, fish2));

        BigDecimal result = calculateFoodService.calculateFoodBaseOnWeights(1);

        // Total weight = 2 + 3 = 5, Food = 5 * 0.02 = 0.1 kg
        assertEquals(BigDecimal.valueOf(0.1), result);
        verify(fishRepository, times(1)).findAllFishWithPondId(1);
    }

    @Test
    public void testGetTotalPondWeight() {
        Fish fish1 = new Fish();
        fish1.setWeight(BigDecimal.valueOf(2.0)); // 2 kg
        Fish fish2 = new Fish();
        fish2.setWeight(BigDecimal.valueOf(3.0)); // 3 kg

        when(fishRepository.findAllFishWithPondId(1)).thenReturn(Arrays.asList(fish1, fish2));

        BigDecimal result = calculateFoodService.getTotalPondWeight(1);

        // Total weight = 2 + 3 = 5 kg
        assertEquals(BigDecimal.valueOf(5.0), result);
        verify(fishRepository, times(1)).findAllFishWithPondId(1);
    }

    @Test
    public void testCalculateFoodBaseOnWeights_NoFishes() {
        when(fishRepository.findAllFishWithPondId(1)).thenReturn(Arrays.asList());

        BigDecimal result = calculateFoodService.calculateFoodBaseOnWeights(1);

        // No fishes, food amount should be 0
        assertEquals(BigDecimal.ZERO, result);
        verify(fishRepository, times(1)).findAllFishWithPondId(1);
    }

    @Test
    public void testGetTotalPondWeight_NoFishes() {
        when(fishRepository.findAllFishWithPondId(1)).thenReturn(Arrays.asList());

        BigDecimal result = calculateFoodService.getTotalPondWeight(1);

        // No fishes, total weight should be 0
        assertEquals(BigDecimal.ZERO, result);
        verify(fishRepository, times(1)).findAllFishWithPondId(1);
    }
}

