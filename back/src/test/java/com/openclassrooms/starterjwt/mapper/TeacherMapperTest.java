package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherMapperTest {

    private TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    public void testToEntity() {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");

        Teacher teacher = teacherMapper.toEntity(teacherDto);

        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());
    }

    @Test
    public void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
        assertEquals(teacher.getLastName(), teacherDto.getLastName());
    }
}
