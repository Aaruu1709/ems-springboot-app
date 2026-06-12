package com.aaruu.ems.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.aaruu.ems.dto.EmmployeeDto;
import com.aaruu.ems.entity.Employee;

@Mapper(componentModel = "spring")

public interface EmployeeMapper {

	EmmployeeDto toDto(Employee employee);

	void updateEmployee(Employee source, @MappingTarget Employee target);

	@BeanMapping(

			nullValuePropertyMappingStrategy =

			NullValuePropertyMappingStrategy.IGNORE

	)

	void patchEmployee(

			Employee source,

			@MappingTarget Employee target

	);
}