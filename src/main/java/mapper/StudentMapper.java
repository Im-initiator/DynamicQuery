package mapper;

import com.leminhtien.demoSpringJpaDynamic.dto.response.StudentDTO;
import com.leminhtien.demoSpringJpaDynamic.entity.StudentEntity;

public class StudentMapper {
	
	public static StudentDTO toDTO(StudentEntity entity) {
		 StudentDTO dto = new StudentDTO();
	        dto.setId(entity.getId());
	        dto.setName(entity.getName());
	        dto.setFullName(entity.getFullName());
	        dto.setAddress(entity.getAddress());
	        dto.setProvince(entity.getProvince());
	        dto.setAge(entity.getAge());
	        dto.setHeight(entity.getHeight());
	        dto.setTuition(entity.getTuition());
	        dto.setDateAdmission(entity.getDateAdmission());
	        return dto;
	}

}
