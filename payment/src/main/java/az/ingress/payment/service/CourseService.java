package az.ingress.payment.service;

import az.ingress.payment.dto.CourseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    CourseDto createAndUpdateCourse(CourseDto courseDto);

    CourseDto getCourse(Long id);

    Page<CourseDto> getAll(Pageable pageable);
}
