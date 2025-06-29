package com.tasnetwork.calibration.energymeter.reportprofile;

import java.util.ArrayList;

import com.tasnetwork.calibration.energymeter.ApplicationLauncher;
import com.tasnetwork.calibration.energymeter.constant.ConstantReportV2;
import com.tasnetwork.spring.orm.model.ReportProfileTestDataFilter;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class ReportTestFilterListReplicateResultCellPositionListComboBoxValueFactory implements Callback<TableColumn.CellDataFeatures<ReportProfileTestDataFilter, ComboBox<String>>, ObservableValue<ComboBox<String>>> {
	   
		@Override
	    public ObservableValue<ComboBox<String>> call(CellDataFeatures<ReportProfileTestDataFilter, ComboBox<String>> param) {
			ReportProfileTestDataFilter rowData = param.getValue();
			ComboBox<String> Combo_Box = new ComboBox<String>();
			ArrayList<String> cellPositionList = new ArrayList<String>();
			int columnWidth = 100;//75;//100;//50;//150;//250//50

			if(rowData.getReplicateResultCellPositionList().size()>0){	
				ArrayList<String> dataList = new ArrayList<String>(rowData.getReplicateResultCellPositionList());
				cellPositionList.addAll(dataList);
			}else {
				cellPositionList.add(ConstantReportV2.NONE_DISPLAYED);
			}
			Combo_Box.getItems().clear();
			Combo_Box.getItems().addAll(cellPositionList);
			Combo_Box.getSelectionModel().select(0);

			Combo_Box.setOnAction((e) -> {
	             ApplicationLauncher.logger.info("ReportTestFilterListReplicateResultCellPositionListComboBoxValueFactory: " + Combo_Box.getSelectionModel().getSelectedItem());
	             //rowData.setTestRunType(Combo_Box.getSelectionModel().getSelectedItem());
	        });
			
			Combo_Box.setMinWidth(columnWidth);
			Combo_Box.setPrefWidth(columnWidth);
			Combo_Box.setMaxWidth(columnWidth);
	        
	        return new SimpleObjectProperty<>(Combo_Box);
	    }


	}