package com.lgcns.hrm.cv.service;

import com.lgcns.hrm.cv.common.utils.BeanUtil;
import com.lgcns.hrm.cv.common.utils.ObjectUtil;
import com.lgcns.hrm.cv.entity.Department;
import com.lgcns.hrm.cv.entity.User;
import com.lgcns.hrm.cv.entity.Group;
import com.lgcns.hrm.cv.exception.ResourceException;
import com.lgcns.hrm.cv.model.vo.UserSearchVo;
import com.lgcns.hrm.cv.model.vo.UserVo;
import com.lgcns.hrm.cv.repository.UserRepository;
import com.lgcns.hrm.cv.repository.GroupRepository;
import com.lgcns.hrm.cv.service.base.AbstractBaseService;
import com.lgcns.hrm.cv.service.specification.UserSpec;
import com.lgcns.hrm.cv.transform.UserTransform;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService extends AbstractBaseService<User, Integer> {

    private final UserRepository userRepository;
    private final DepartmentService departmentService;
    private final UserTransform userTransform;
    private final GroupRepository groupRepository;

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    @Transactional
    public void saveOrUpdate(UserVo typeVo) throws Exception {
        User user = ObjectUtil.isEmpty(typeVo.getId()) ?
                new User() : userRepository.findById(typeVo.getId()).orElse(new User());
        if (ObjectUtil.isNotEmpty(user.getId())) {
            BeanUtil.copyNonNull(typeVo, user);
        } else {
            user = userTransform.toEntity(typeVo);
        }
        List<Group> groups = ObjectUtil.isEmpty(typeVo.getGroupIds()) ? Collections.emptyList() : groupRepository.findAllById(typeVo.getGroupIds());
        user.setGroups(groups);
        if (ObjectUtils.isEmpty(user.getDepartment().getId())) {
            throw new ResourceException("Department ID cannot be null");
        }
        Department checkExistedDepartment = departmentService.findById(user.getDepartment().getId());
        if (Objects.isNull(checkExistedDepartment)) {
            throw new ResourceException("No department with id: " + user.getDepartment().getId() + " existed");
        }
        userRepository.saveAndFlush(user);
    }

    public User getById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public List<UserVo> getAllByConditions(UserSearchVo userSearchParam) {
        Specification<User> conditionQuery = UserSpec.findByCondition(userSearchParam);
        return userTransform.mapEntityListIntoDtoList(userRepository.findAll(conditionQuery));
    }

    public Page<UserVo> getUsers(UserSearchVo userSearchParam, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<User> conditionQuery = UserSpec.findByCondition(userSearchParam);
        Page<User> users = userRepository.findAll(conditionQuery, pageable);
        return userTransform.mapEntityPageIntoDtoPage(users);
    }

    private static boolean isExcelFile(String filePath) {
        if (filePath == null) {
            return false;
        }
        // Check if the file extension is .xls or .xlsx
        return filePath.toLowerCase().endsWith(".xls") || filePath.toLowerCase().endsWith(".xlsx");
    }

    private User convertExcelRowToObj(Row row, Authentication authentication) {
        User tempUser = new User();
        tempUser.setName(row.getCell(2).getStringCellValue());
//        tempEmployee.setCode(String.valueOf(row.getCell(1).getNumericCellValue()));
//        tempEmployee.setDevSide(row.getCell(5).getStringCellValue());
//        tempEmployee.setDuty(row.getCell(6).getStringCellValue());
//        tempEmployee.setTypeOfJob(row.getCell(7).getStringCellValue());
        tempUser.setPhoneNumber(row.getCell(10).getStringCellValue());
        tempUser.setEmail(row.getCell(11).getStringCellValue());
        tempUser.setSex(row.getCell(9).getStringCellValue());
        double age = row.getCell(8).getNumericCellValue();
//        tempEmployee.setAge((int) age);
        tempUser.setCreateBy(authentication.getName());
        Department tempDepartment = departmentService.getbyCode(row.getCell(4).getStringCellValue());
        tempUser.setDepartment(tempDepartment);
        return tempUser;
    }

    private static final DataFormatter dataFormatter = new DataFormatter();

    @Transactional
    public void importExcel(MultipartFile excelFile) throws ResourceException {
        String fileName = excelFile.getOriginalFilename();
        if (!isExcelFile(fileName)) {
            throw new ResourceException("Invalid Excel");
        }
        try {
            List<User> userList = new ArrayList<>();
            Workbook workbook;
            if (fileName.toLowerCase().endsWith(".xls")) {
                workbook = new HSSFWorkbook(excelFile.getInputStream());
            } else {
                workbook = new XSSFWorkbook(excelFile.getInputStream());
            }
            Sheet worksheet = workbook.getSheetAt(0);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            IntStream.iterate(1, i -> i + 1)
                    .mapToObj(worksheet::getRow)
                    .takeWhile(row -> row != null && !dataFormatter.formatCellValue(row.getCell(0)).isEmpty())
                    .map(row -> convertExcelRowToObj(row, authentication))
                    .forEach(userList::add);
            userRepository.saveAll(userList);
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

}
