package com.gmail.merikbest2015.springbootreactjscrudapp.service;

import com.gmail.merikbest2015.springbootreactjscrudapp.model.Employee;
import com.gmail.merikbest2015.springbootreactjscrudapp.repository.EmployeeRepository;
import com.gmail.merikbest2015.springbootreactjscrudapp.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    @DisplayName("Should return all employees when findAll is called")
    void should_return_all_employees_when_findAll_is_called() {
        Employee employee1 = createEmployee(1L, "John", "Doe", "New York", "123 Main St", "1234567890");
        Employee employee2 = createEmployee(2L, "Jane", "Smith", "Los Angeles", "456 Oak Ave", "0987654321");
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.findAll();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return employee when findById is called with existing id")
    void should_return_employee_when_findById_is_called_with_existing_id() {
        Long employeeId = 1L;
        Employee employee = createEmployee(employeeId, "John", "Doe", "New York", "123 Main St", "1234567890");

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.findById(employeeId);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("Should return empty optional when findById is called with non-existing id")
    void should_return_empty_optional_when_findById_is_called_with_non_existing_id() {
        Long employeeId = 999L;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.findById(employeeId);

        assertFalse(result.isPresent());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("Should save employee when save is called with valid employee")
    void should_save_employee_when_save_is_called_with_valid_employee() {
        Employee employee = createEmployee(null, "John", "Doe", "New York", "123 Main St", "1234567890");
        Employee savedEmployee = createEmployee(1L, "John", "Doe", "New York", "123 Main St", "1234567890");

        when(employeeRepository.save(employee)).thenReturn(savedEmployee);

        Employee result = employeeService.save(employee);

        assertNotNull(result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("Should delete employee when delete is called")
    void should_delete_employee_when_delete_is_called() {
        Employee employee = createEmployee(1L, "John", "Doe", "New York", "123 Main St", "1234567890");

        doNothing().when(employeeRepository).delete(employee);

        employeeService.delete(employee);

        verify(employeeRepository, times(1)).delete(employee);
    }

    private Employee createEmployee(Long id, String firstName, String lastName, String city, String address, String telephone) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setCity(city);
        employee.setAddress(address);
        employee.setTelephone(telephone);
        return employee;
    }
}
