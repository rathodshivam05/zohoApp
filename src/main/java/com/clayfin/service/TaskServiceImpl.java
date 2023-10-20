 package com.clayfin.service;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clayfin.entity.Employee;
import com.clayfin.entity.Task;
import com.clayfin.enums.RoleType;
import com.clayfin.enums.TaskStatus;
import com.clayfin.exception.EmployeeException;
import com.clayfin.exception.TaskException;
import com.clayfin.repository.EmployeeRepo;
import com.clayfin.repository.TaskRepo;
import com.clayfin.utility.Constants;
import com.clayfin.utility.RepoHelper;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepo taskRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private RepoHelper repoHelper;

	private Employee userinfo(Principal user) {
		Employee employee = employeeRepo.findByUsername(user.getName());
		return employee;
	}
	
	@Override
	public Task assingTask(Task task, Integer managerId, Integer employeeId,Principal user) throws TaskException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {


		Employee employee = employeeRepo.findById(employeeId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + employeeId));

		if (employee.getManager() == null)
			throw new EmployeeException("No Manager Assinged to the Employee " + employeeId);

		if (!employee.getManager().getEmployeeId().equals(managerId))
			throw new EmployeeException("You are Not a Authorized Manager for Employee Id " + employeeId);

		task.setAssignedTime(LocalDateTime.now());
		task.setEmployee(employee);
		task.setStatus(TaskStatus.PENDING);
		task.setEmployeeName(employee.getUsername());
		return taskRepo.save(task);
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public Task updateTask(Integer taskId, Task task,Principal user) throws TaskException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {


		if (!repoHelper.isTaskExist(taskId)) {
			throw new TaskException(Constants.TASK_NOT_FOUND_WITH_ID + taskId);
		}
		Task task1 = getTaskById(taskId,user); 
		BeanUtils.copyProperties(task, task1, getNullPropertyNames(task));
			return taskRepo.save(task1);
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
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
	public Task deleteTask(Integer taskId,Principal user) throws TaskException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {


		Task task = taskRepo.findById(taskId)
				.orElseThrow(() -> new TaskException(Constants.TASK_NOT_FOUND_WITH_ID + taskId));

		taskRepo.delete(task);

		return task;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public Task getTaskById(Integer taskId,Principal user) throws TaskException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {

Task task = taskRepo.findById(taskId)
				.orElseThrow(() -> new TaskException(Constants.TASK_NOT_FOUND_WITH_ID + taskId));

		return task;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<Task> getAllTaskByEmployeeId(Integer employeeId,Principal user) throws EmployeeException, TaskException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {


		List<Task> tasks = taskRepo.findByEmployeeEmployeeId(employeeId);

		if (tasks.isEmpty())
			throw new TaskException(Constants.TASK_NOT_FOUND_WITH_EMPLOYEE_ID + employeeId);

		return tasks;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<Task> getAllTaskAssignedByManagerId(Integer managerId,Principal user) throws EmployeeException, TaskException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {


		Employee manager = employeeRepo.findById(managerId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + managerId));

		if (!manager.getRole().equals(RoleType.ROLE_MANAGER))
			throw new EmployeeException(Constants.YOU_ARE_NOT_A_MANAGER);

		List<Task> tasks = taskRepo.findByEmployeeManagerEmployeeId(managerId);

		if (tasks.isEmpty())
			throw new TaskException(Constants.TASK_NOT_FOUND_WITH_MANAGER_ID + managerId);

		return tasks;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<Task> getAllTaskByStatusAndEmployeeId(Integer employeeId, TaskStatus status,Principal user)
			throws EmployeeException, TaskException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(employeeId)) {

List<Task> tasks = taskRepo.findByEmployeeEmployeeIdAndStatus(employeeId, status);

		if (tasks.isEmpty())
			throw new TaskException(Constants.TASK_NOT_FOUND_WITH_EMPLOYEE_ID + employeeId);

		return tasks;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<Task> getAllTaskByDateAndEmployeeId(Integer employeeId, LocalDate date,Principal user)
			throws EmployeeException, TaskException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Task> getAllTaskByStatusAndManagerId(Integer managerId, TaskStatus status,Principal user)
			throws EmployeeException, TaskException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {


		Employee manager = employeeRepo.findById(managerId)
				.orElseThrow(() -> new EmployeeException(Constants.EMPLOYEE_NOT_FOUND_WITH_ID + managerId));

		if (!manager.getRole().equals(RoleType.ROLE_MANAGER))
			throw new EmployeeException(Constants.YOU_ARE_NOT_A_MANAGER);

		List<Task> tasks = taskRepo.findByEmployeeManagerEmployeeIdAndStatus(managerId, status);

		if (tasks.isEmpty())
			throw new TaskException(Constants.TASK_NOT_FOUND_WITH_MANAGER_ID + managerId);

		return tasks;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public List<Task> getAllTaskByDateAndManagerId(Integer managerId, LocalDate date,Principal user)
			throws EmployeeException, TaskException {
		Employee currentUser = userinfo(user);

		if (currentUser.getEmployeeId().equals(managerId)) {

// TODO Auto-generated method stub
		return null;
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

	@Override
	public Task updateTaskStatus(Integer taskId, TaskStatus status,Principal user) throws TaskException, EmployeeException {
		Employee currentUser = userinfo(user);

		if (repoHelper.isEmployeeExist(currentUser.getEmployeeId())) {

Task task = taskRepo.findById(taskId)
				.orElseThrow(() -> new TaskException(Constants.TASK_NOT_FOUND_WITH_ID + taskId));

		if (task.getStatus() != TaskStatus.PENDING)
			throw new TaskException("Task Already Updated to " + task.getStatus());

		task.setStatus(status);

		return taskRepo.save(task);
		}
		else {
					throw new EmployeeException(Constants.RESTRICTED_TO_ACCESS_OTHERS_DATA);
				}
	}

}
