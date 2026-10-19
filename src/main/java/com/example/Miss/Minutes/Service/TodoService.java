package com.example.Miss.Minutes.Service;


import com.example.Miss.Minutes.Dto.CreateTodoDto;
import com.example.Miss.Minutes.Dto.ResponseTodoDto;
import com.example.Miss.Minutes.Dto.UpdateTodoDto;
import com.example.Miss.Minutes.Entity.Todo;
import com.example.Miss.Minutes.Exception.TodoNotFoundException;
import com.example.Miss.Minutes.Exception.TodoValidationFailed;
import com.example.Miss.Minutes.Repository.TodoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TodoService {

    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }


    @Transactional
    public ResponseTodoDto createTodoDto(CreateTodoDto createTodoDto) {

        if (createTodoDto.getTitle() == null
                || createTodoDto.getTitle().isBlank()
                || createTodoDto.getDescription() == null
                || createTodoDto.getDescription().isBlank()) {

            throw new TodoValidationFailed(
                    "Please enter the required fields"
            );
        }

        Todo saveTodo = mapToEntity(createTodoDto);

        Todo dtoRequest = todoRepository.save(saveTodo);

        return mapToDto(dtoRequest);
    }


    //getting Todobyid
    public ResponseTodoDto getById(Long id){

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found " + id ));

        return mapToDto(todo);
    }

    //getting all Todos

    public List<ResponseTodoDto> getAllTodo(){

        List<Todo> allTodo = todoRepository.findAll();

        //mapping my alltodo with maptoDto
        return allTodo.stream().map(this::mapToDto).toList();
    }

    //get todo by today date
    public List<ResponseTodoDto> getTodoByToday(){

        LocalDate today = LocalDate.now();

        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end  = today.plusDays(1).atStartOfDay();

        List<Todo> todoToday = todoRepository.findAllByDay(start,end);

        return  todoToday.stream().map(this::mapToDto).toList();
    }

    //update task
    public ResponseTodoDto updateTodo(Long id, UpdateTodoDto updateTodoDto) {

        System.out.println("Incoming completed: "
                + updateTodoDto.getCompleted());

        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));




        if (updateTodoDto.getTitle() != null) {
            existingTodo.setTitle(updateTodoDto.getTitle());
        }

        if (updateTodoDto.getDescription() != null) {
            existingTodo.setDescription(updateTodoDto.getDescription());
        }

        if (updateTodoDto.getCompleted() != null) {
            existingTodo.setCompleted(updateTodoDto.getCompleted());
        }

        System.out.println("Before save: " + existingTodo.getCompleted());

        Todo savedTodo = todoRepository.save(existingTodo);

        System.out.println("After save: " + savedTodo.getCompleted());

        return mapToDto(savedTodo);
    }

    //delete
    public  void deleteTodo(Long id){

        Todo deleteTodo = todoRepository.findById(id).orElseThrow();

        todoRepository.delete(deleteTodo);
    }

    //mapping todo to Entity
    public Todo mapToEntity(CreateTodoDto todoRequester ){

            Todo todo = new Todo();

            todo.setTitle(todoRequester.getTitle());
            todo.setDescription(todoRequester.getDescription());
            todo.setCompleted(false);

            return todo;

    }




    //Mapping updated to  Entity
    public  Todo mapToUpdateEntity(UpdateTodoDto updateTodoDto){

        Todo todo = new Todo();

        todo.setTitle(updateTodoDto.getTitle());
        todo.setDescription(updateTodoDto.getDescription());
        todo.setCompleted(updateTodoDto.isCompleted());

        return todo;
    }

    //Mapping Dto to Entity
    public ResponseTodoDto mapToDto(Todo todo) {
        ResponseTodoDto dto = new ResponseTodoDto();

        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setCompleted(todo.getCompleted());
        dto.setCreatedAt(todo.getCreatedAt());

        return dto;
    }
}
