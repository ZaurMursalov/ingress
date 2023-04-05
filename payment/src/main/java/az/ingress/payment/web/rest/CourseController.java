package az.ingress.payment.web.rest;

import az.ingress.common.exception.ApplicationException;
import az.ingress.payment.dto.CourseDto;
import az.ingress.payment.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/courses")
public class CourseController {
    private final CourseService courseService;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto createAndUpdateCourse(@RequestBody CourseDto courseDto) {
        log.info("Create and update course , {}", courseDto);
        return courseService.createAndUpdateCourse(courseDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CourseDto getCourse(@PathVariable Long id){
        log.trace("Get course by id {}", id);
        return courseService.getCourse(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    public Page<CourseDto> getAll(Pageable pageable){
        log.trace("List all courses");
        return courseService.getAll(pageable);
    }
}
