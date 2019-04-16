package com.todo.ui;

import com.todo.editor.TaskEditor;
import com.todo.entity.Task;
import com.todo.entity.TasksHolder;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.Data;

@UIScope
@Data
@Route(value = "todo")
@Theme(value = Lumo.class, variant = "dark")
public class MainView extends VerticalLayout implements RouterLayout {

    private TaskEditor taskEditor;
    private Grid<Task> grid;
    private Binder<Task> binder;
    private Button addNewBtn;
    private Button delete;
    private TasksHolder tasksHolder;
    private Checkbox headerCheckbox;
    private HorizontalLayout actions;

    public void init() {
        addNewBtn.setText("Add Task");
        addNewBtn.setIcon(VaadinIcon.PLUS.create());
        delete.setText("Delete");
        delete.setIcon(VaadinIcon.TRASH.create());
        delete.addClickListener(e -> delete());
        delete.getElement().getThemeList().add("error");
        actions.add(addNewBtn, delete);
        add(actions, grid, taskEditor);

        headerCheckbox.setValue(false);

        grid.setItems(tasksHolder.getTasks());
        grid.addComponentColumn(task -> {
            Checkbox inlineCheckbox = getCheckbox();
            inlineCheckbox.setValue(task.isCheckbox());
            binder.readBean(task);
            binder.forMemberField(inlineCheckbox).bind("checkbox");
            headerCheckbox.addValueChangeListener(e -> {
                inlineCheckbox.setValue(headerCheckbox.getValue());
            });
            inlineCheckbox.addValueChangeListener(event -> {
                try {
                    binder.writeBean(task);
                    task.setCheckbox(inlineCheckbox.getValue());
                } catch (ValidationException e) {
                    e.printStackTrace();
                }
            });
            return inlineCheckbox;
        }).setResizable(true).setId("CH2");
        grid.getColumnByKey("taskItem").setResizable(true);
        grid.removeColumnByKey("checkbox");
        grid.getHeaderRows().get(0).getCells().forEach(headerCell -> headerCell.setComponent(headerCheckbox));

        grid.setHeightByRows(true);
        grid.asSingleSelect().addValueChangeListener(task -> taskEditor.editTask(task.getValue()));

        addNewBtn.addClickListener(e -> taskEditor.editTask(Task.builder()
                                                                .checkbox(false).taskItem("")
                                                                .build()));

        taskEditor.setChangeHandler(() -> {
            taskEditor.setVisible(false);
            listTasks();
        });
    }

    void listTasks() {
        grid.setItems(tasksHolder.getTasks());
    }

    void delete() {
        tasksHolder.getTasks().removeIf(task -> task.isCheckbox());
        headerCheckbox.setValue(false);
        listTasks();
    }

    public Checkbox getCheckbox() {
        return null;
    }

    public Button getButton() {
        return null;
    }
}
