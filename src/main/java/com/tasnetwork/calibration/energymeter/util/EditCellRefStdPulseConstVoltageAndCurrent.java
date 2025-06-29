package com.tasnetwork.calibration.energymeter.util;

import com.tasnetwork.calibration.energymeter.ApplicationLauncher;
import com.tasnetwork.calibration.energymeter.setting.RefStdConstController;
import com.tasnetwork.calibration.energymeter.setting.RefStdPulseConstantModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class EditCellRefStdPulseConstVoltageAndCurrent <S, T> extends TextFieldTableCell<S, T> {

	private TextField textField;
	private boolean escapePressed = false;
	private TablePosition<S, ?> tablePos = null;
	
	private static TableView<?> presentActiveTable = null;

	public EditCellRefStdPulseConstVoltageAndCurrent(final StringConverter<T> converter) {
		super(converter);
	}

	public static <S> Callback<TableColumn<S, String>, TableCell<S, String>> forTableColumn() {
		return forTableColumn(new DefaultStringConverter());
	}

	public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(
			final StringConverter<T> converter) {
		return list -> new EditCellRefStdPulseConstVoltageAndCurrent<S, T>(converter);
	}

	@Override
	public void startEdit() {
		if (!isEditable() || !getTableView().isEditable()
				|| !getTableColumn().isEditable()) {
			return;
		}
		super.startEdit();

		if (isEditing()) {
			if (textField == null) {
				textField = getTextField();
			}
			escapePressed = false;
			startEdit(textField);
			final TableView<S> table = getTableView();
			tablePos = table.getEditingCell();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void commitEdit(T newValue) {
		if (!isEditing())
			return;
		final TableView<S> table = getTableView();
		if (table != null) {
			// Inform the TableView of the edit being ready to be committed.
			CellEditEvent editEvent = new CellEditEvent(table, tablePos,
					TableColumn.editCommitEvent(), newValue);

			Event.fireEvent(getTableColumn(), editEvent);
		}
		// we need to setEditing(false):
		super.cancelEdit(); // this fires an invalid EditCancelEvent.
		// update the item within this cell, so that it represents the new value
		updateItem(newValue, false);
		if (table != null) {
			// reset the editing cell on the TableView
			table.edit(-1, null);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void cancelEdit() {
		if (escapePressed) {
			// this is a cancel event after escape key
			super.cancelEdit();
			setText(getItemText()); // restore the original text in the view
			//setGraphic(null);
			//setContentDisplay(ContentDisplay.TEXT_ONLY);
		} else {
			// this is not a cancel event after escape key
			// we interpret it as commit.
			String newText = textField.getText();
			// commit the new text to the model
			this.commitEdit(getConverter().fromString(newText));
		}
		setGraphic(null); // stop editing with TextField
	}

	/** {@inheritDoc} */
	@Override
	public void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		updateItem();
	}

	private TextField getTextField() {

		final TextField textField = new TextField(getItemText());

		textField.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ApplicationLauncher.logger.info("getTextField:Test1");
			}
		});

		// Use onAction here rather than onKeyReleased (with check for Enter),
		textField.setOnAction(event -> {
			if (getConverter() == null) {
				throw new IllegalStateException("StringConverter is null.");
			}
			this.commitEdit(getConverter().fromString(textField.getText()));
			event.consume();
		});

		textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					commitEdit(getConverter().fromString(textField.getText()));
				}
			}
		});

		textField.setOnKeyPressed(t -> {
			if (t.getCode() == KeyCode.ESCAPE)
				escapePressed = true;
			else
				escapePressed = false;
		});
		textField.setOnKeyReleased(t -> {
			if (t.getCode() == KeyCode.ESCAPE) {
				throw new IllegalArgumentException(
						"did not expect esc key releases here.");
			}
		});

		textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ESCAPE) {
				textField.setText(getConverter().toString(getItem()));
				cancelEdit();
				event.consume();
			} else if (event.getCode() == KeyCode.RIGHT
					|| (event.getCode() == KeyCode.TAB && (!event.isShiftDown())  )) {
				getTableView().getSelectionModel().selectNext();
				ApplicationLauncher.logger.debug("EditCellRefStdPulseConstVoltageAndCurrent: getTextField: Tab");
				RefStdConstController.editFocusedCellOnTableViewVoltageAndCurrentMapping();
				//editFocusedCell(getPresentActiveTable());
				event.consume();
			} else if ( (event.getCode() == KeyCode.LEFT) 
					|| (event.getCode() == KeyCode.TAB && (event.isShiftDown())  )) {
				getTableView().getSelectionModel().selectPrevious();
				
				//editFocusedCell(getPresentActiveTable());
				RefStdConstController.editFocusedCellOnTableViewVoltageAndCurrentMapping();
				event.consume();
			} else if (event.getCode() == KeyCode.UP) {
				getTableView().getSelectionModel().selectAboveCell();
				//editFocusedCell(getPresentActiveTable());
				RefStdConstController.editFocusedCellOnTableViewVoltageAndCurrentMapping();
				event.consume();
			} else if (event.getCode() == KeyCode.DOWN) {
				getTableView().getSelectionModel().selectBelowCell();
				RefStdConstController.editFocusedCellOnTableViewVoltageAndCurrentMapping();
				//editFocusedCell(getPresentActiveTable());
				event.consume();
			}
		});

		return textField;
	}
	 
/*	public static void editFocusedCell(TableView<?> ref_tableView) {
		ApplicationLauncher.logger.debug("editFocusedCell: Entry");
		final TablePosition<Object, ?> focusedCell = 
				ref_tableView.focusModelProperty().get().focusedCellProperty().get();
		if(focusedCell.getTableColumn().isEditable()){
			ApplicationLauncher.logger.debug("editFocusedCell: Edit Hit");
			ref_tableView.edit(focusedCell.getRow(), focusedCell.getTableColumn());
		}
	}
	*/
/*	@SuppressWarnings("unchecked")
	public void editFocusedCell(TableColumn<?, ?> ref_tableView) {
		ApplicationLauncher.logger.debug("editFocusedCell: Entry");
		final TablePosition<?, ?> focusedCell = 
				ref_tableView.focusModelProperty().get().focusedCellProperty().get();
		if(focusedCell.getTableColumn().isEditable()){
			ApplicationLauncher.logger.debug("editFocusedCell: Edit Hit");
			ref_tableView.edit(focusedCell.getRow(), focusedCell.getTableColumn());
		}
	}*/

	private String getItemText() {
		return getConverter() == null
				? getItem() == null ? "" : getItem().toString()
				: getConverter().toString(getItem());
	}

	private void updateItem() {
		if (isEmpty()) {
			setText(null);
			setGraphic(null);
		} else {
			if (isEditing()) {
				if (textField != null) {
					textField.setText(getItemText());
				}
				setText(null);
				setGraphic(textField);
			} else {
				setText(getItemText());
				setGraphic(null);
			}
		}
	}

	private void startEdit(final TextField textField) {
		if (textField != null) {
			textField.setText(getItemText());
		}
		setText(null);
		setGraphic(textField);
		textField.selectAll();
		// requesting focus so that key input can immediately go into the
		// TextField
		textField.requestFocus();
	}

	public static TableView<?> getPresentActiveTable() {
		return presentActiveTable;
	}

	public static void setPresentActiveTable(TableView<?> presentActiveTable) {
		EditCellRefStdPulseConstVoltageAndCurrent.presentActiveTable = presentActiveTable;
	}
}

