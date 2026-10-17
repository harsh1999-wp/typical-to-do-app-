package com.example.Miss.Minutes.Service;


import com.example.Miss.Minutes.Dto.CreateTodoDto;
import com.example.Miss.Minutes.Dto.ResponseTodoDto;
import com.example.Miss.Minutes.Dto.UpdateTodoDto;
import com.example.Miss.Minutes.Entity.Todo;
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
    public ResponseTodoDto createTodoDto(CreateTodoDto createTodoDto ){


        Todo saveTodo = mapToEntity(createTodoDto);

        Todo dtoRequest = todoRepository.save(saveTodo);

        return mapToDto(dtoRequest);
    }

    //getting Todobyid
    public ResponseTodoDto getById(Long id){

        Todo todo = todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));

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
    public Todo updateTodo(Long id , UpdateTodoDto updateTodoDto){


        Todo existingTodo = todoRepository.findById(id).orElseThrow(
                () ->new RuntimeException("Todo not found")
        );

        existingTodo.setTitle(updateTodoDto.getTitle());
        existingTodo.setDescription(updateTodoDto.getDescription());
        existingTodo.setCompleted(updateTodoDto.isCompleted());

        return todoRepository.save(existingTodo);

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

    //Mapping Dto to Entity
    public ResponseTodoDto mapToDto(Todo todo){

        ResponseTodoDto responseTodoDto = new ResponseTodoDto();

        responseTodoDto.setTitle(todo.getTitle());
        responseTodoDto.setDescription(todo.getDescription());

        return responseTodoDto;
    }

    //Mapping updated to  Entity
    public  Todo mapToUpdateEntity(UpdateTodoDto updateTodoDto){

        Todo todo = new Todo();

        todo.setTitle(updateTodoDto.getTitle());
        todo.setDescription(updateTodoDto.getDescription());
        todo.setCompleted(updateTodoDto.isCompleted());

        return todo;
    }
}
