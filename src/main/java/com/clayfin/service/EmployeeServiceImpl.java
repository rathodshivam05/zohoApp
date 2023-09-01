package com.clayfin.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clayfin.entity.Attendance;
import com.clayfin.entity.Employee;
import com.clayfin.entity.EmployeeProfile;
import com.clayfin.entity.LeaveRecord;
import com.clayfin.entity.Task;
import com.clayfin.enums.RoleType;
import com.clayfin.exception.AttendanceException;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.LeaveException;
import com.clayfin.exception.TaskException;
import com.clayfin.repository.EmployeeProfileRepo;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.utility.Constants;
import com.clayfin.utility.RepoHelper;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeProfileRepo employeeProfileRepo;

	@Autowired
	private RepoHelper repoHelper;

	@Override
	public Employee addEmployee(Employee employee) throws EmployeeException {

		if (employee == null)
			throw new EmployeeException();

		return employeeRepo.save(employee);
	}

	@Override
	public Employee updateEmployee(Integer employeeId, Employee employee) throws EmployeeException {
		Employee employee1 = getEmployeeById(employeeId);
		if (repoHelper.isEmployeeExist(employeeId)) {
			BeanUtils.copyProperties(employee, employee1, getNullPropertyNames(employee));
			return employeeRepo.save(employee1);
		}
		else
			throw new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId);
	}
	
	
	private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

	@Override
	public Employee getEmployeeById(Integer employeeId) throws EmployeeException {
		return employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));
	}

	@Override
	public Employee deleteEmployee(Integer employeeId) throws EmployeeException {
		Employee employee = getEmployeeById(employeeId);
		employeeRepo.deleteById(employeeId);
		return employee;
	}

	@Override
	public Employee getEmployeeByEmail(String email) throws EmployeeException {
		return employeeRepo.findByEmail(email)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_EMAIL + email));

	}

	@Override
	public List<Employee> getAllEmployees() throws EmployeeException {
		List<Employee> employees = employeeRepo.findAll();

		if (employees.isEmpty())
			throw new EmployeeException(Constants.EMPLOYEES_NOT_FOUND);

		return employees;

	}

	@Override
	public Employee getEmployeeManager(Integer employeeId) throws EmployeeException {

		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeException(Constants.TASK_NOT_FOUND_WITH_EMPLOYEE_ID + employeeId));

		if (employee.getManager() == null)
			throw new EmployeeException("Manager Not Assigned To the Employee Id " + employeeId);

		return employee.getManager();

	}

	@Override
	public List<Employee> getEmployeesOfManager(Integer managerId) throws EmployeeException {

		Employee employee = employeeRepo.findById(managerId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID));

		if (employee.getSubEmployees().isEmpty())
			throw new EmployeeException(Constants.EMPLOYEES_NOT_FOUND);

		return employee.getSubEmployees();

	}

	@Override
	public List<Task> getAllTaskMyEmployeeId(Integer employeeId) throws EmployeeException, TaskException {

		Employee employee = getEmployeeById(employeeId);

		if (employee.getTasks().isEmpty()) {
			throw new TaskException(Constants.TASK_NOT_FOUND_WITH_EMPLOYEE_ID + employeeId);
		}

		return employee.getTasks();
	}

	@Override
	public List<LeaveRecord> getAllLeavesByEmployeeId(Integer employeeId) throws EmployeeException, LeaveException {
		Employee employee = getEmployeeById(employeeId);

		if (employee.getTasks().isEmpty()) {
			throw new LeaveException(Constants.LEAVE_NOT_FOUND_WITH_EMPLOYEE_ID + employeeId);
		}

		return employee.getLeaveRecords();
	}

	@Override
	public List<Attendance> getAllAttendanceByEmployeeId(Integer employeeId)
			throws EmployeeException, AttendanceException {
		Employee employee = getEmployeeById(employeeId);

		if (employee.getTasks().isEmpty()) {
			throw new AttendanceException(Constants.ATTENDANCE_NOT_FOUND_WITH_EMPLOYEE_ID + employeeId);
		}

		return employee.getAttendances();

	}

	@Override
	public Employee setManagerToEmployee(Integer employeeId, Integer managerId) throws EmployeeException {

		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));
		Employee manager = employeeRepo.findById(managerId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + managerId));

		if (manager.getRole() != RoleType.ROLE_MANAGER)
			throw new EmployeeException("Manager Id Not Correct");

		if (managerId == employeeId)
			throw new EmployeeException("Manager Himself Cannot me Manager");

		if (employee.getManager() != null)
			throw new EmployeeException("Employee Manager Already Exist");

		employee.setManager(manager);

		employeeRepo.save(employee);

		return employee;
	}

	@Override
	public Employee updateSkillSet(Integer employeeId, List<String> skills) throws EmployeeException {
		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));
		
            List<String> skillSet = employee.getSkillSet();
            skills.forEach(i -> skillSet.add(i));
            employee.setSkillSet(skillSet);
		return employeeRepo.save(employee);
	}
	
	@Override
	public EmployeeProfile getEmployeeProfileByEmployeeId(Integer employeeId) throws EmployeeException {
		if (repoHelper.isEmployeeExist(employeeId))
			return employeeProfileRepo.findByEmployeeEmployeeId(employeeId);
		else
			throw new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId);
	}
	
	@Override
	public EmployeeProfile addEmployeeProfileData(Integer employeeId, EmployeeProfile employeeProfile) throws EmployeeException {

		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));
		
		employeeProfile.setEmployee(employee);
		employeeProfile.setReportingTo(employeeRepo.findById(employee.getManager().getEmployeeId()).get().getUsername());
		return employeeProfileRepo.save(employeeProfile);
	
	}

}
