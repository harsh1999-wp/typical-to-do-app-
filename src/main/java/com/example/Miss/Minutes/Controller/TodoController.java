package com.example.Miss.Minutes.Controller;


import com.example.Miss.Minutes.Dto.CreateTodoDto;
import com.example.Miss.Minutes.Dto.ResponseTodoDto;
import com.example.Miss.Minutes.Dto.UpdateTodoDto;
import com.example.Miss.Minutes.Entity.Todo;
import com.example.Miss.Minutes.Service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/todo/v1")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    //createtodo
    @PostMapping("/create")
    public ResponseEntity<ResponseTodoDto> createTodo(@RequestBody CreateTodoDto createTodoDto){
        ResponseTodoDto todoResponse = todoService.createTodoDto(createTodoDto);

        return  ResponseEntity.ok().body(todoResponse);
    }




    //gettodobyid
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTodoDto> getTodoById(@PathVariable Long id){
        ResponseTodoDto todoById = todoService.getById(id);

        return ResponseEntity.ok(todoById);
    }



    //getalltodos
    @GetMapping()
    public ResponseEntity<List<ResponseTodoDto>> getAllTodo(){
        List<ResponseTodoDto> allTodo =  todoService.getAllTodo();

        return ResponseEntity.ok(allTodo);
    }


    //gettodobydate
    @GetMapping("/today")
    public ResponseEntity<List<ResponseTodoDto>> getTodoByToday(){
        List<ResponseTodoDto> todoToday = todoService.getTodoByToday();

        return ResponseEntity.ok(todoToday);
    }

    //UpdateTodo
    @PatchMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id ,@RequestBody  UpdateTodoDto updateTodoDto){

        Todo todoDto = todoService.updateTodo(id , updateTodoDto);

        return ResponseEntity.ok(todoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id){

        todoService.deleteTodo(id);

        return ResponseEntity.ok("Deleted Successfully Now fu*k off");

    }
}
