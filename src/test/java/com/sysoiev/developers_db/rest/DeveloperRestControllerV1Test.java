package com.sysoiev.developers_db.rest;

import com.sysoiev.developers_db.builder.CommonBuilder;
import com.sysoiev.developers_db.builder.DeveloperBuilder;
import com.sysoiev.developers_db.dto.DeveloperDto;
import com.sysoiev.developers_db.model.Developer;
import com.sysoiev.developers_db.service.DeveloperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperRestControllerV1Test {

    @Mock
    private DeveloperService developerService;

    @InjectMocks
    private DeveloperRestControllerV1 developerRestController;

    @Test
    public void get() {

        Developer developer = DeveloperBuilder.developerDb("firstName", "lastName").build();

        when(developerService.getById(developer.getId())).thenReturn(developer);
        ResponseEntity<DeveloperDto> response = developerRestController.get(developer.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(developer.getFirstName(), Objects.requireNonNull(response.getBody()).getFirstName());
        assertEquals(developer.getLastName(), Objects.requireNonNull(response.getBody()).getLastName());
        assertEquals(developer.getId(), response.getBody().getId());
    }

    @Test
    public void getFail() {

        ResponseEntity<DeveloperDto> response1 = developerRestController.get(null);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

        Long id = CommonBuilder.id("1");
        when(developerService.getById(id)).thenReturn(null);
        ResponseEntity<DeveloperDto> response2 = developerRestController.get(id);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
    }

    @Test
    public void getAll() {
        Developer developer1 = DeveloperBuilder.developerDb("firstName1", "lastName1").build();
        Developer developer2 = DeveloperBuilder.developerDb("firstName2", "lastName2").id(CommonBuilder.id("2")).build();
        List<Developer> developers = CommonBuilder.list(developer1, developer2);

        when(developerService.list()).thenReturn(developers);

        ResponseEntity<List<DeveloperDto>> response = developerRestController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(developers.size(), Objects.requireNonNull(response.getBody()).size());
        assertEquals(developer1.getId(), response.getBody().get(0).getId());
        assertEquals(developer2.getId(), response.getBody().get(1).getId());
    }

    @Test
    public void getAllFail() {

        when(developerService.list()).thenReturn(new ArrayList<>());
        ResponseEntity<List<DeveloperDto>> response = developerRestController.getAll();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void save() {

        Developer developer = DeveloperBuilder.developer("firstName1", "lastName1").build();

        ResponseEntity<Developer> response = developerRestController.save(developer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(developer.getFirstName(), Objects.requireNonNull(response.getBody()).getFirstName());
        assertEquals(developer.getLastName(), Objects.requireNonNull(response.getBody()).getLastName());
        assertEquals(developer.getSalary(), Objects.requireNonNull(response.getBody()).getSalary());

        verify(developerService, times(1)).save(developer);
    }

    @Test
    public void saveFail() {
        ResponseEntity<Developer> response = developerRestController.save(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(developerService, never()).save(any(Developer.class));
    }

    @Test
    public void update() {
        Developer developer = DeveloperBuilder.developer("firstName2", "lastName2").build();

        Long id = CommonBuilder.id("1");
        ResponseEntity<Developer> response = developerRestController.update(id, developer);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(developer.getFirstName(), Objects.requireNonNull(response.getBody()).getFirstName());
        assertEquals(developer.getLastName(), Objects.requireNonNull(response.getBody()).getLastName());

        verify(developerService, times(1)).update(id, developer);
    }

    @Test
    public void updateFail() {
        ResponseEntity<Developer> response = developerRestController.update(null, null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        verify(developerService, never()).update(anyLong(), any(Developer.class));
    }

    @Test
    public void delete() {

        Developer developer = DeveloperBuilder.developerDb("firstName1", "lastName1").build();
        when(developerService.getById(developer.getId())).thenReturn(developer);

        ResponseEntity response = developerRestController.delete(developer.getId());

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

        verify(developerService, times(1)).delete(developer.getId());
    }

    @Test
    public void deleteFail() {

        Long id = CommonBuilder.id("1");
        when(developerService.getById(id)).thenReturn(null);

        ResponseEntity response = developerRestController.delete(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(developerService, never()).delete(anyLong());
    }
}
