package com.koi.FA24_SE1854_SWP391_G6_KoiCareHome;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.exception.AlreadyExistedException;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.exception.NotFoundException;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.model.Fish;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.model.FishType;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.repository.FishRepository;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.repository.FishTypeRepository;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.service.FishService;
import com.koi.FA24_SE1854_SWP391_G6_KoiCareHome.service.FishTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

public class FishServiceTest {

    @Mock
    private FishRepository fishRepository;

    @Mock
    private FishTypeRepository fishTypeRepository;

    @Mock
    private FishTypeService fishTypeService;

    @InjectMocks
    private FishService fishService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveFish_whenFishNameExists_shouldThrowAlreadyExistedException() {
        Fish fish = new Fish();
        fish.setName("Nemo");
        fish.setPondID(1);
        fish.setFishID(2);
        fish.setMemberID(3);

        when(fishRepository.existsByNameAndPondIdExceptId("Nemo", 2, 1)).thenReturn(true);

        assertThrows(AlreadyExistedException.class, () -> {
            fishService.saveFish(fish);
        });
    }

    @Test
    public void testSaveFish_whenMemberNotFound_shouldThrowNotFoundException() {
        Fish fish = new Fish();
        fish.setName("Nemo");
        fish.setPondID(1);
        fish.setMemberID(999);

        when(fishRepository.existsByNameAndPondIdExceptId("Nemo", 0, 1)).thenReturn(false);
        when(fishRepository.existsMemberId(999)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            fishService.saveFish(fish);
        });
    }

    @Test
    public void testSaveFish_whenFishTypeNotFound_shouldCreateFishType() {
        Fish fish = new Fish();
        fish.setName("Nemo");
        fish.setPondID(1);
        fish.setFishID(0);
        fish.setMemberID(3);

        when(fishRepository.existsByNameAndPondIdExceptId("Nemo", 0, 1)).thenReturn(false);
        when(fishRepository.existsMemberId(3)).thenReturn(true);
        when(fishTypeRepository.findByName("KoiFish")).thenReturn(Optional.empty());

        when(fishTypeRepository.findByName("KoiFish")).thenReturn(Optional.of(new FishType(1, "KoiFish", true, LocalDateTime.now(), "test", LocalDateTime.now(), "test")));

        Fish savedFish = fishService.saveFish(fish);
        assertNotNull(savedFish);
        verify(fishTypeService).saveFishTypeByName("KoiFish");
        verify(fishRepository).save(fish);
    }

    @Test
    public void testGetAllFishes() {
        fishService.getAllFishes();
        verify(fishRepository).findAllFish();
    }

    @Test
    public void testUpdateFish_whenFishExists() {
        Fish existingFish = new Fish();
        existingFish.setFishID(1);
        existingFish.setName("Nemo");

        when(fishRepository.findFishById(1)).thenReturn(Optional.of(existingFish));
        when(fishRepository.existsByNameAndPondIdExceptId(anyString(), anyInt(), anyInt())).thenReturn(false);

        Fish updatedFish = new Fish();
        updatedFish.setName("Dory");

        Fish returnedFish = fishService.updateFish(1, updatedFish);
        assertEquals("Dory", returnedFish.getName());
        verify(fishRepository).save(existingFish);
    }

    @Test
    public void testDeleteByID_whenFishExists() {
        Fish fish = new Fish();
        fish.setFishID(1);

        when(fishRepository.existsById(1)).thenReturn(true);
        when(fishRepository.findById(1)).thenReturn(Optional.of(fish));

        assertDoesNotThrow(() -> fishService.deleteByID(1));
        verify(fishRepository).deleteByID(1);
    }

    @Test
    public void testDeleteByID_whenFishNotFound() {
        when(fishRepository.existsById(1)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            fishService.deleteByID(1);
        });
    }
    @Test
    void contextLoads() {
    }
}


