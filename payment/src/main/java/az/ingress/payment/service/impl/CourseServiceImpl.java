package az.ingress.payment.service.impl;

import az.ingress.common.exception.ApplicationException;
import az.ingress.payment.domain.Course;
import az.ingress.payment.dto.CourseDto;
import az.ingress.payment.errors.Errors;
import az.ingress.payment.repository.CourseRepository;
import az.ingress.payment.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    @Override
    public CourseDto createAndUpdateCourse(CourseDto dto) {
        Course course = isUpdate(dto.getId()) ? updateCourse(dto) : createCourse(dto);
        return mapper.map(courseRepository.save(course), CourseDto.class);
    }

    private Course createCourse(CourseDto dto) {
        return Course.builder()
                .name(dto.getName())
                .courseGroup(dto.getCourseGroup())
                .build();
    }

    @Override
    public CourseDto getCourse(Long id) {
        return mapper.map(fetchCourseIfExist(id), CourseDto.class);
    }

    @Override
    public Page<CourseDto> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(entity -> mapper.map(entity, CourseDto.class));
    }

    private Course fetchCourseIfExist(Long id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new ApplicationException(Errors.COURSE_ID_NOT_FOUND, Map.of("id", id)));
    }

    private Course updateCourse(CourseDto dto) {
        Course course = fetchCourseIfExist(dto.getId());
        course.setName(dto.getName());
        course.setCourseGroup(dto.getCourseGroup());
        return course;
    }


    private boolean isUpdate(Long id) {
        return id != null;
    }

}
