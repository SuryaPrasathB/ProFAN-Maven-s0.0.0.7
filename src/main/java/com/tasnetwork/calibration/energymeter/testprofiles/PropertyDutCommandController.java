package com.tasnetwork.calibration.energymeter.testprofiles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import com.tasnetwork.calibration.energymeter.ApplicationLauncher;
import com.tasnetwork.calibration.energymeter.constant.ConstantApp;
import com.tasnetwork.calibration.energymeter.constant.ConstantDut;
import com.tasnetwork.calibration.energymeter.constant.ProcalFeatureEnable;
import com.tasnetwork.calibration.energymeter.database.MySQL_Controller;
import com.tasnetwork.calibration.energymeter.device.DeviceDataManagerController;
import com.tasnetwork.calibration.energymeter.project.ProjectController;
import com.tasnetwork.calibration.energymeter.util.GuiUtils;
import com.tasnetwork.spring.orm.model.DutCommand;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.Cursor;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class PropertyDutCommandController extends AnchorPane {

	//static long loadedDutCommandId = 0;
	
	Timer SaveDataTimer;
	@FXML TextField txtTestType;
	public static TextField ref_txtTestType;

	@FXML TextField txt_testcasename;
	public static TextField ref_txt_testcasename;


	@FXML TextField txtResponseTimeoutInSec;
	public static TextField ref_txtResponseTimeoutInSec;

	@FXML TextField txtAlias_ID;
	public static TextField ref_txtAlias_ID;

	@FXML TextField txtTotalExecutionTimeInSec;
	public static TextField ref_txtTotalExecutionTimeInSec;

	@FXML ComboBox<String> cmbBxSetSerialNoSource;
	public static ComboBox<String> ref_cmbBxSetSerialNoSource;	

	/*	@FXML TextField txt_volt1;
	public static TextField ref_txt_volt1;

	@FXML TextField txt_volt2;
	public static TextField ref_txt_volt2;

	@FXML TextField txt_volt3;
	public static TextField ref_txt_volt3;	

	@FXML TextField txt_current1;
	public static TextField ref_txt_current1;

	@FXML TextField txt_current2;
	public static TextField ref_txt_current2;

	@FXML TextField txt_current3;
	public static TextField ref_txt_current3;	

	@FXML TextField txt_phase1;
	public static TextField ref_txt_phase1;

	@FXML TextField txt_phase2;
	public static TextField ref_txt_phase2;

	@FXML TextField txt_phase3;
	public static TextField ref_txt_phase3;	*/



	/*@FXML TextField txt_frequency;
	public static TextField ref_txt_frequency;*/	

	@FXML TextField txtSendCommandData;
	public static TextField ref_txtSendCommandData;	

	@FXML TextField txtResponseTerminator;
	public static TextField ref_txtResponseTerminator;	

	@FXML TextField txtSendCommandTerminator;
	public static TextField ref_txtSendCommandTerminator;

	@FXML TextField txtHaltTimeInSec;
	public static TextField ref_txtHaltTimeInSec;

	@FXML CheckBox chkBxWriteSerialNoToDut;
	public static CheckBox ref_chkBxWriteSerialNoToDut;	

	@FXML CheckBox chkBxReadSerialNoFromDut;
	public static CheckBox ref_chkBxReadSerialNoFromDut;	


	/*	@FXML TextField txt_emax;
	public static TextField ref_txt_emax;	

	@FXML TextField txt_time_duration;
	public static TextField ref_txt_time_duration;	*/

	@FXML TextField txtResponseExpectedData;
	public static TextField ref_txtResponseExpectedData;	


	@FXML CheckBox chkBxCmdInHexMode;
	public static CheckBox ref_chkBxCmdInHexMode;	

	@FXML CheckBox chkBoxResponseMandatory;
	public static CheckBox ref_chkBoxResponseMandatory;


	@FXML CheckBox chkBxCmdTerminatorInHexMode;
	public static CheckBox ref_chkBxCmdTerminatorInHexMode;	

	@FXML CheckBox chkBxResponseTerminatorInHexMode;
	public static CheckBox ref_chkBxResponseTerminatorInHexMode;





	/*	@FXML TextField txt_skip_readings;
	public static TextField ref_txt_skip_readings;	*/

	/*	@FXML ComboBox<String> comBox_test_run_type;
	public static ComboBox<String> ref_comBox_test_run_type;	*/

	/*	@FXML RadioButton rdBtnHexFormat;
	public static RadioButton ref_rdBtnHexFormat;	

	@FXML RadioButton rdBtnAsciiFormat;
	public static RadioButton ref_rdBtnAsciiFormat;	*/

	/*	@FXML RadioButton radio_btn_ph1_lead;
	public static RadioButton ref_radio_btn_ph1_lead;

	@FXML RadioButton radio_btn_ph2_lead;
	public static RadioButton ref_radio_btn_ph2_lead;	

	@FXML RadioButton radio_btn_ph3_lead;
	public static RadioButton ref_radio_btn_ph3_lead;

	@FXML RadioButton radio_btn_ph1_lag;
	public static RadioButton ref_radio_btn_ph1_lag;

	@FXML RadioButton radio_btn_ph2_lag;
	public static RadioButton ref_radio_btn_ph2_lag;	

	@FXML RadioButton radio_btn_ph3_lag;
	public static RadioButton ref_radio_btn_ph3_lag;	*/

	public static DraggableTestNode mCurrentNode=null;

	@FXML Button btn_Save;

	/*	@FXML  Label A_PhaseLabel;
	@FXML  Label B_PhaseLabel;
	@FXML  Label C_PhaseLabel;

	@FXML  Label B_PhaseVoltUnit;
	@FXML  Label C_PhaseVoltUnit;
	@FXML  Label B_PhaseCurrentUnit;
	@FXML  Label C_PhaseCurrentUnit;
	@FXML  Label labelBalanceType;*/

	@FXML  Label lbl_SkipReading;
	@FXML  Label lbl_Emin ;
	@FXML  Label lbl_Emax ;
	@FXML  Label lbl_NoOfPulses ;
	@FXML  Label lbl_Duration ;
	@FXML  Label lbl_Minute ;
	@FXML  Label lbl_RunType ;

	public static Label ref_lbl_SkipReading ;
	public static Label ref_lbl_Emin ;
	public static Label ref_lbl_Emax ;
	public static Label ref_lbl_NoOfPulses ;
	public static Label ref_lbl_Duration ;
	public static Label ref_lbl_Minute ;
	public static Label ref_lbl_RunType ;

	private String saveFailedReason = "";

	@FXML
	private void initialize() throws IOException {

		/*		if(ProcalFeatureEnable.PHASE_DISPLAY_ENABLE_FEATURE){
			A_PhaseLabel.setText(ConstantApp.FIRST_PHASE_DISPLAY_NAME + " Phase");
			B_PhaseLabel.setText(ConstantApp.SECOND_PHASE_DISPLAY_NAME + " Phase");
			C_PhaseLabel.setText(ConstantApp.THIRD_PHASE_DISPLAY_NAME + " Phase");
		}*/
		ref_txtTestType = txtTestType;
		ref_txt_testcasename = txt_testcasename;
		ref_txtAlias_ID = txtAlias_ID;
		ref_txtResponseTimeoutInSec = txtResponseTimeoutInSec;
		ref_cmbBxSetSerialNoSource = cmbBxSetSerialNoSource;
		ref_txtTotalExecutionTimeInSec = txtTotalExecutionTimeInSec;
		/*		ref_txt_volt1 = txt_volt1;
		ref_txt_volt2 = txt_volt2;
		ref_txt_volt3 = txt_volt3;

		ref_txt_current1 = txt_current1;
		ref_txt_current2 = txt_current2;
		ref_txt_current3 = txt_current3;

		ref_txt_phase1 = txt_phase1;
		ref_txt_phase2 = txt_phase2;
		ref_txt_phase3 = txt_phase3;*/

		ref_lbl_SkipReading = lbl_SkipReading;
		ref_lbl_Emin = lbl_Emin;
		ref_lbl_Emax = lbl_Emax;
		ref_lbl_NoOfPulses = lbl_NoOfPulses;
		ref_lbl_Duration = lbl_Duration;
		ref_lbl_Minute = lbl_Minute;
		ref_lbl_RunType = lbl_RunType;



		/*		ref_radio_btn_ph1_lead = radio_btn_ph1_lead;
		ref_radio_btn_ph2_lead = radio_btn_ph2_lead;
		ref_radio_btn_ph3_lead = radio_btn_ph3_lead;
		ref_radio_btn_ph1_lag = radio_btn_ph1_lag;
		ref_radio_btn_ph2_lag = radio_btn_ph2_lag;
		ref_radio_btn_ph3_lag = radio_btn_ph3_lag;*/

		//ref_txt_frequency = txt_frequency;

		ref_txtSendCommandData = txtSendCommandData;
		//ref_txt_emax = txt_emax;
		//ref_txt_time_duration = txt_time_duration;
		ref_txtResponseExpectedData = txtResponseExpectedData;

		ref_txtSendCommandTerminator = txtSendCommandTerminator;
		ref_txtResponseTerminator = txtResponseTerminator;
		//ref_txt_skip_readings = txt_skip_readings;
		//	ref_comBox_test_run_type = comBox_test_run_type;

		ref_chkBoxResponseMandatory = chkBoxResponseMandatory;
		ref_chkBxCmdInHexMode = chkBxCmdInHexMode;

		ref_chkBxWriteSerialNoToDut = chkBxWriteSerialNoToDut;
		ref_chkBxReadSerialNoFromDut = chkBxReadSerialNoFromDut;


		ref_chkBxCmdTerminatorInHexMode = chkBxCmdTerminatorInHexMode;
		ref_chkBxResponseTerminatorInHexMode = chkBxResponseTerminatorInHexMode;

		ref_txtHaltTimeInSec = txtHaltTimeInSec;
		/*		ref_rdBtnHexFormat = rdBtnHexFormat;
		ref_rdBtnAsciiFormat = rdBtnAsciiFormat;
		ref_rdBtnHexFormat.setSelected(true);*/
		/*		AutoFill_Voltage();
		AutoFill_Current();
		AutoFill_Phase();*/
		if((ConstantApp.USER_ACCESS_LEVEL.equals(ConstantApp.TESTER_ACCESS_LEVEL)) ||
				(ConstantApp.USER_ACCESS_LEVEL.equals(ConstantApp.READONLY_ACCESS_LEVEL))){
			btn_Save.setDisable(true);
		}
		String metertype = ProjectController.getProjectEM_ModelType();
		if(metertype.contains(ConstantApp.METERTYPE_SINGLEPHASE)){
			/*B_PhaseLabel.setVisible(false);
			//ref_txt_volt2.setVisible(false);
			B_PhaseVoltUnit.setVisible(false);
			//ref_txt_current2.setVisible(false);
			B_PhaseCurrentUnit.setVisible(false);
			//ref_txt_phase2.setVisible(false);
			//ref_radio_btn_ph2_lag.setVisible(false);
			//ref_radio_btn_ph2_lead.setVisible(false);
			C_PhaseLabel.setVisible(false);

			//ref_txt_volt3.setVisible(false);
			C_PhaseVoltUnit.setVisible(false);
			//ref_txt_current3.setVisible(false);
			C_PhaseCurrentUnit.setVisible(false);*/
			//ref_txt_phase3.setVisible(false);
			//ref_radio_btn_ph3_lag.setVisible(false);
			//ref_radio_btn_ph3_lead.setVisible(false);
			//labelBalanceType.setVisible(false);
			//ref_rdBtnAsciiFormat.setSelected(true);
			//ref_rdBtnHexFormat.setSelected(false);
			//ref_rdBtnHexFormat.setVisible(false);
			//ref_rdBtnAsciiFormat.setVisible(false);

		}
		//RunTypeInit();
		//cbxRunTypeOnChange();

		if(ProcalFeatureEnable.USER_ACCESS_CONTROL_ENABLED){
			applyUacSettings();
		}

		if(ProcalFeatureEnable.PROPOWER_SRC_ONLY ){
			ref_txtSendCommandData.setVisible(false);
			//ref_txt_emax.setVisible(false);
			ref_txtResponseExpectedData.setVisible(false);
			//ref_txt_time_duration.setVisible(false);
			//ref_txt_skip_readings.setVisible(false);
			//ref_comBox_test_run_type.setVisible(false);

			lbl_SkipReading.setVisible(false);
			lbl_Emin.setVisible(false);
			lbl_Emax.setVisible(false);
			lbl_NoOfPulses.setVisible(false);
			lbl_Duration.setVisible(false);
			lbl_Minute.setVisible(false);
			ref_lbl_RunType.setVisible(false);
		}

		ref_cmbBxSetSerialNoSource.getItems().setAll(ConstantDut.SET_SERIAL_NO_SOURCE_TYPE_LIST);
		ref_cmbBxSetSerialNoSource.getSelectionModel().select(0);
	}


	private void applyUacSettings() {
		
		ApplicationLauncher.logger.debug("PropertyDutCommandController:  applyUacSettings: Entry");
		if(!ProjectController.isChildPropertySaveEnabled()){
			btn_Save.setDisable(true);
		}

	}

	public void RunTypeInit(){
		ArrayList<String> run_Type = new ArrayList<String>();

		/*		if(ProcalFeatureEnable.RUN_TYPE_TIME_BASED_ENABLED){
			run_Type.add(ConstantApp.TESTPOINT_RUNTYPE_TIMEBASED);
		}*/
		run_Type.add(ConstantApp.TESTPOINT_RUNTYPE_PULSEBASED);
		if(ProcalFeatureEnable.RUN_TYPE_TIME_BASED_ENABLED){
			run_Type.add(ConstantApp.TESTPOINT_RUNTYPE_TIMEBASED);
		}
		/*
		ref_comBox_test_run_type.getItems().clear();
		ref_comBox_test_run_type.getItems().addAll(run_Type);
		ref_comBox_test_run_type.getSelectionModel().select(0) ;*/
	}

	public static  void PropertyDisplayUpdate(String testType, String aliasId,DraggableTestNode SelectedNode){

		mCurrentNode = SelectedNode;

		ApplicationLauncher.logger.info("testType: " + testType);
		ApplicationLauncher.logger.info("aliasId: " + aliasId);
		ref_txtTestType.setText(testType);
		ref_txtAlias_ID.setText(aliasId);


		new ArrayList<String>();



		/*		setLagSelected(ref_radio_btn_ph1_lag, ref_radio_btn_ph1_lead);
		setLagSelected(ref_radio_btn_ph2_lag, ref_radio_btn_ph2_lead);
		setLagSelected(ref_radio_btn_ph3_lag, ref_radio_btn_ph3_lead);*/

		if(mCurrentNode.IsNewNode() != true){
			load_saved_data(testType, aliasId);
		}
		//cbxRunTypeOnChangeStatic();
	}

	public static void setLagSelected(RadioButton lag, RadioButton lead){
		lag.setSelected(true);
		lead.setSelected(false);
	}

	public static void setLeadSelected(RadioButton lag, RadioButton lead){
		lag.setSelected(false);
		lead.setSelected(true);
	}

	/*public  void cbxRunTypeOnChange(){
		try{
			if (ref_comBox_test_run_type.getSelectionModel().getSelectedItem().toString().equals(ConstantApp.TESTPOINT_RUNTYPE_TIMEBASED)){

				ref_txt_time_duration.setEditable(true);
				ref_txtExpectedResponseData.setEditable(false);
				ref_txt_skip_readings.setEditable(false);

			}else{
				ref_txt_time_duration.setEditable(false);
				ref_txtExpectedResponseData.setEditable(true);
				ref_txt_skip_readings.setEditable(true);
				if(ref_txt_time_duration.getText().isEmpty()||ref_txt_time_duration.getText().equals("0")){
					ref_txt_time_duration.setText("2");
				}

			}
		}catch(Exception e){
			e.printStackTrace();
			ApplicationLauncher.logger.error("cbxRunTypeOnChange: Exception:" + e.getMessage());
		}
	}
	 */
	/*	public static void cbxRunTypeOnChangeStatic(){
		try{
			if (ref_comBox_test_run_type.getSelectionModel().getSelectedItem().toString().equals(ConstantApp.TESTPOINT_RUNTYPE_TIMEBASED)){

				ref_txt_time_duration.setEditable(true);
				ref_txtExpectedResponseData.setEditable(false);
				ref_txt_skip_readings.setEditable(false);

			}else{
				ref_txt_time_duration.setEditable(false);
				ref_txtExpectedResponseData.setEditable(true);
				ref_txt_skip_readings.setEditable(true);
				if(ref_txt_time_duration.getText().isEmpty()||ref_txt_time_duration.getText().equals("0")){
					ref_txt_time_duration.setText("2");
				}

			}
		}catch(Exception e){
			e.printStackTrace();
			ApplicationLauncher.logger.error("cbxRunTypeOnChangeStatic: Exception:" + e.getMessage());
		}
	}
	 */
	/*	public void Balanced_on_click(){
		if(ref_rdBtnAsciiFormat.isSelected()){
			ref_rdBtnHexFormat.setSelected(false);
			ref_txt_volt2.setEditable(false);
			ref_txt_volt3.setEditable(false);
			ref_txt_current2.setEditable(false);
			ref_txt_current3.setEditable(false);
			ref_txt_phase2.setEditable(false);
			ref_txt_phase3.setEditable(false);

			ref_radio_btn_ph2_lag.setDisable(true);
			ref_radio_btn_ph2_lead.setDisable(true);
			ref_radio_btn_ph3_lag.setDisable(true);
			ref_radio_btn_ph3_lead.setDisable(true);
		}
		else{
			ref_rdBtnHexFormat.setSelected(true);
			Unbalanced_on_click();
		}
	}*/

	/*	public void Unbalanced_on_click(){
		if(ref_rdBtnHexFormat.isSelected()){
			ref_rdBtnAsciiFormat.setSelected(false);
			ref_txt_volt2.setEditable(true);
			ref_txt_volt3.setEditable(true);
			ref_txt_current2.setEditable(true);
			ref_txt_current3.setEditable(true);
			ref_txt_phase2.setEditable(true);
			ref_txt_phase3.setEditable(true);

			ref_radio_btn_ph2_lag.setDisable(false);
			ref_radio_btn_ph2_lead.setDisable(false);
			ref_radio_btn_ph3_lag.setDisable(false);
			ref_radio_btn_ph3_lead.setDisable(false);
		}
		else{
			ref_rdBtnAsciiFormat.setSelected(true);
			Balanced_on_click();
		}
	}*/

	/*	public void AutoFill_Voltage(){
		ref_txt_volt1.textProperty().addListener((observable, oldValue, newValue) -> {
			if(ref_rdBtnAsciiFormat.isSelected()){
				ref_txt_volt2.setText(ref_txt_volt1.getText());
				ref_txt_volt3.setText(ref_txt_volt1.getText());
			}
		});
	}

	public void AutoFill_Current(){
		ref_txt_current1.textProperty().addListener((observable, oldValue, newValue) -> {
			if(ref_rdBtnAsciiFormat.isSelected()){
				ref_txt_current2.setText(ref_txt_current1.getText());
				ref_txt_current3.setText(ref_txt_current1.getText());
			}
		});
	}*/

	//	public void AutoFill_Phase(){
	/*		ref_txt_phase1.textProperty().addListener((observable, oldValue, newValue) -> {
			if(ref_rdBtnAsciiFormat.isSelected()){
				ref_txt_phase2.setText(ref_txt_phase1.getText());
				ref_txt_phase3.setText(ref_txt_phase1.getText());
			}
		});*/

	/*		ref_radio_btn_ph1_lag.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if(ref_rdBtnAsciiFormat.isSelected()){
					ref_radio_btn_ph2_lag.setDisable(false);
					ref_radio_btn_ph3_lag.setDisable(false);
					ref_radio_btn_ph2_lead.setDisable(false);
					ref_radio_btn_ph3_lead.setDisable(false);
					if (isNowSelected) { 
						ref_radio_btn_ph1_lag.setSelected(true);
						ref_radio_btn_ph2_lag.setSelected(true);
						ref_radio_btn_ph3_lag.setSelected(true);
						ref_radio_btn_ph1_lead.setSelected(false);
						ref_radio_btn_ph2_lead.setSelected(false);
						ref_radio_btn_ph3_lead.setSelected(false);
					} else {
						ref_radio_btn_ph1_lag.setSelected(false);
						ref_radio_btn_ph2_lag.setSelected(false);
						ref_radio_btn_ph3_lag.setSelected(false);
						ref_radio_btn_ph1_lead.setSelected(true);
						ref_radio_btn_ph2_lead.setSelected(true);
						ref_radio_btn_ph3_lead.setSelected(true);
					}
					ref_radio_btn_ph2_lag.setDisable(true);
					ref_radio_btn_ph2_lag.setDisable(true);
					ref_radio_btn_ph2_lead.setDisable(true);
					ref_radio_btn_ph3_lead.setDisable(true);
				}
				else{
					if (isNowSelected) { 
						ref_radio_btn_ph1_lag.setSelected(true);
						ref_radio_btn_ph1_lead.setSelected(false);
					} else {
						ref_radio_btn_ph1_lag.setSelected(false);
						ref_radio_btn_ph1_lead.setSelected(true);
					}
				}
			}
		});*/

	/*ref_radio_btn_ph1_lead.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if(ref_rdBtnAsciiFormat.isSelected()){
					ref_radio_btn_ph2_lag.setDisable(false);
					ref_radio_btn_ph3_lag.setDisable(false);
					ref_radio_btn_ph2_lead.setDisable(false);
					ref_radio_btn_ph3_lead.setDisable(false);
					if (isNowSelected) { 
						ref_radio_btn_ph1_lag.setSelected(false);
						ref_radio_btn_ph2_lag.setSelected(false);
						ref_radio_btn_ph3_lag.setSelected(false);
						ref_radio_btn_ph1_lead.setSelected(true);
						ref_radio_btn_ph2_lead.setSelected(true);
						ref_radio_btn_ph3_lead.setSelected(true);
					} else {
						ref_radio_btn_ph1_lag.setSelected(true);
						ref_radio_btn_ph2_lag.setSelected(true);
						ref_radio_btn_ph3_lag.setSelected(true);
						ref_radio_btn_ph1_lead.setSelected(false);
						ref_radio_btn_ph2_lead.setSelected(false);
						ref_radio_btn_ph3_lead.setSelected(false);
					}
					ref_radio_btn_ph2_lag.setDisable(true);
					ref_radio_btn_ph3_lag.setDisable(true);
					ref_radio_btn_ph2_lead.setDisable(true);
					ref_radio_btn_ph3_lead.setDisable(true);
				}
				else{
					if (isNowSelected) { 
						ref_radio_btn_ph1_lag.setSelected(false);
						ref_radio_btn_ph1_lead.setSelected(true);
					} else {
						ref_radio_btn_ph1_lag.setSelected(true);
						ref_radio_btn_ph1_lead.setSelected(false);
					}
				}
			}
		});

		ref_radio_btn_ph2_lag.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (isNowSelected) { 
					ref_radio_btn_ph2_lag.setSelected(true);
					ref_radio_btn_ph2_lead.setSelected(false);
				} else {
					ref_radio_btn_ph2_lag.setSelected(false);
					ref_radio_btn_ph2_lead.setSelected(true);
				}
			}
		});

		ref_radio_btn_ph2_lead.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (isNowSelected) { 
					ref_radio_btn_ph2_lag.setSelected(false);
					ref_radio_btn_ph2_lead.setSelected(true);
				} else {
					ref_radio_btn_ph2_lag.setSelected(true);
					ref_radio_btn_ph2_lead.setSelected(false);
				}
			}
		});

		ref_radio_btn_ph3_lag.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (isNowSelected) { 
					ref_radio_btn_ph3_lag.setSelected(true);
					ref_radio_btn_ph3_lead.setSelected(false);
				} else {
					ref_radio_btn_ph3_lag.setSelected(false);
					ref_radio_btn_ph3_lead.setSelected(true);
				}
			}
		});

		ref_radio_btn_ph3_lead.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
				if (isNowSelected) { 
					ref_radio_btn_ph3_lag.setSelected(false);
					ref_radio_btn_ph3_lead.setSelected(true);
				} else {
					ref_radio_btn_ph3_lag.setSelected(true);
					ref_radio_btn_ph3_lead.setSelected(false);
				}
			}
		});
	 */

	//	}
	/*
	public static void setLagLeadRadiobtns(String ph1, String ph2, String ph3){
		if(ph1.equals(ConstantApp.PF_LAG)){
			setLagSelected(ref_radio_btn_ph1_lag, ref_radio_btn_ph1_lead);
		}
		else{
			setLeadSelected(ref_radio_btn_ph1_lag, ref_radio_btn_ph1_lead);
		}
		if(ph2.equals(ConstantApp.PF_LAG)){
			setLagSelected(ref_radio_btn_ph2_lag, ref_radio_btn_ph2_lead);
		}
		else{
			setLeadSelected(ref_radio_btn_ph2_lag, ref_radio_btn_ph2_lead);
		}
		if(ph3.equals(ConstantApp.PF_LAG)){
			setLagSelected(ref_radio_btn_ph3_lag, ref_radio_btn_ph3_lead);
		}
		else{
			setLeadSelected(ref_radio_btn_ph3_lag, ref_radio_btn_ph3_lead);
		}
	}*/

	public static String getLagLeadValueFromRadiobtns(RadioButton lag){
		if(lag.isSelected()){
			return ConstantApp.PF_LAG;
		}
		else{
			return ConstantApp.PF_LEAD;
		}
	}

	public static void load_saved_data(String testType, String aliasId){
		String project_name = mCurrentNode.getProjectName();
		new ArrayList<String>();

		Optional<DutCommand> dutCommandOpt = DeviceDataManagerController.getDutCommandService().findFirstByProjectNameAndTestAliasId(project_name, aliasId);
		try {
			//JSONArray testcase = testdetailslist.getJSONArray("test_details");
			//JSONObject test = testcase.getJSONObject(0);
			/*			String u1 = test.getString("cus_voltage_u1");
			String u2 = test.getString("cus_voltage_u2");
			String u3 = test.getString("cus_voltage_u3");
			String i1 = test.getString("cus_current_i1");
			String i2 = test.getString("cus_current_i2");
			String i3 = test.getString("cus_current_i3");
			String ph1 = test.getString("cus_phase_ph1");
			String ph2 = test.getString("cus_phase_ph2");
			String ph3 = test.getString("cus_phase_ph3");
			String freq = test.getString("cus_frequency");*/

			//String time = test.getString("time_duration");



			if(dutCommandOpt.isPresent()) {
				DutCommand dutCommand = dutCommandOpt.get();

				String targetCommand =  dutCommand.getTargetCmd();// //test.getString("target_cmd");
				boolean targetCommandInHexMode =  dutCommand.isTargetCmdInHex();//test.getString("target_cmd_in_hex");
				String targetCommandTerminator = dutCommand.getTargetCmdTerminator();// test.getString("target_cmd_terminator");
				boolean targetCommandTerminatorInHexMode =  dutCommand.isTargetCmdTerminatorInHex();//test.getString("target_cmd_terminator_in_hex");
				boolean responseMandatory =  dutCommand.isResponseMandatory();//test.getString("response_mandatory");
				String responseExpectedData = dutCommand.getResponseExpectedData() ;//test.getString("response_expected_data");
				String responseTerminator = dutCommand.getResponseTerminator();// test.getString("response_terminator");
				boolean responseTerminatorInHexMode =  dutCommand.getResponseTerminatorInHex();//test.getString("response_terminator_in_hex");
				int responseTimeOutInSec =  dutCommand.getResponseTimeOutInSec();//test.getString("response_time_out_in_sec");
				int responseAsciiLength =  dutCommand.getResponseAsciiLength();//test.getString("response_ascii_length");
				

				ApplicationLauncher.logger.debug("PropertyDutCommandController: responseAsciiLength: " + responseAsciiLength);


				boolean writeSerialNoToDut = dutCommand.isWriteSerialNoToDut();//test.getString("write_serial_no_to_dut");		
				if(writeSerialNoToDut) {
					ref_chkBxWriteSerialNoToDut.setSelected(true);
				}else {
					ref_chkBxWriteSerialNoToDut.setSelected(false);
				}

				boolean readSerialNoFromDut = dutCommand.isReadSerialNoFromDut();//test.getString("read_serial_no_from_dut");

				if(readSerialNoFromDut) {
					ref_chkBxReadSerialNoFromDut.setSelected(true);
				}else {
					ref_chkBxReadSerialNoFromDut.setSelected(false);
				}


				int haltTimeInSec = dutCommand.getHaltTimeInSec();//test.getString("halt_time_in_sec");//ref_txtHaltTimeInSec.getText(); 
				ref_txtHaltTimeInSec.setText(String.valueOf(haltTimeInSec));
				int totalDutExecutionTimeInSec = dutCommand.getTotalDutExecutionTimeInSec();//test.getString("total_dut_execution_time_in_sec");//ref_txtTotalExecutionTimeInSec.getText();
				ref_txtTotalExecutionTimeInSec.setText(String.valueOf(totalDutExecutionTimeInSec));
				String setSerialNoSourceType = dutCommand.getSerialNoSourceType();//test.getString("serial_no_source_type");//ref_cmbBxSetSerialNoSource.getSelectionModel().getSelectedItem().toString();
				ref_cmbBxSetSerialNoSource.getSelectionModel().select(setSerialNoSourceType);

				//String emax = test.getString("inf_emax");
				//String expectedResponse = test.getString("expected_response");
				//String responseMandatory = test.getString("response_mandatory");
				//String commandInHex = test.getString("cmd_in_hex");
				//String testruntype = test.getString("testruntype");
				//String skip_reading = test.getString("skip_reading_count");
				String test_case_name = dutCommand.getTestCaseName();//test.getString("test_case_name");
				String[] testname_parts = test_case_name.split("_");
				String testname =  testname_parts[0];
				/*			ArrayList<String> ph1_value = getPFValue(ph1); 
				ArrayList<String> ph2_value = getPFValue(ph2); 
				ArrayList<String> ph3_value = getPFValue(ph3); */
				/*			ref_txt_volt1.setText(u1);
				ref_txt_volt2.setText(u2);
				ref_txt_volt3.setText(u3);
				ref_txt_current1.setText(i1);
				ref_txt_current2.setText(i2);
				ref_txt_current3.setText(i3);
				ref_txt_phase1.setText(ph1_value.get(0));
				ref_txt_phase2.setText(ph2_value.get(0));
				ref_txt_phase3.setText(ph3_value.get(0));

				setLagLeadRadiobtns(ph1_value.get(1), ph2_value.get(1), ph3_value.get(1));*/
				//ref_txt_frequency.setText(freq);

				//ref_txt_time_duration.setText(time);
				ref_txtSendCommandData.setText(targetCommand)	;		//ref_txt_emax.setText(emax);
				if(targetCommandInHexMode) {
					ref_chkBxCmdInHexMode.setSelected(true);
				}else {
					ref_chkBxCmdInHexMode.setSelected(false);
				}
				ref_txtSendCommandTerminator.setText(targetCommandTerminator);
				if(targetCommandTerminatorInHexMode) {
					ref_chkBxCmdTerminatorInHexMode.setSelected(true);
				}else {
					ref_chkBxCmdTerminatorInHexMode.setSelected(false);
				}


				ref_txtResponseExpectedData.setText(responseExpectedData);
				if(responseMandatory) {
					ref_chkBoxResponseMandatory.setSelected(true);
				}else {
					ref_chkBoxResponseMandatory.setSelected(false);
				}


				ref_txtResponseTerminator.setText(responseTerminator);
				if(responseTerminatorInHexMode) {
					ref_chkBxResponseTerminatorInHexMode.setSelected(true);
				}else {
					ref_chkBxResponseTerminatorInHexMode.setSelected(false);
				}

				ref_txtResponseTimeoutInSec.setText(String.valueOf(responseTimeOutInSec));




				//ref_txt_skip_readings.setText(skip_reading);
				//ref_comBox_test_run_type.setValue(testruntype);
				ref_txt_testcasename.setText(testname);
				//setLoadedDutCommandId(dutCommand.getId());
			}
			
			







		} catch (JSONException e) {
			
			e.printStackTrace();
			ApplicationLauncher.logger.error("PropertyDutCommandController: load_saved_data: JSONException:"+e.getMessage());

		}
	}




	public static ArrayList<String> getPFValue(String input){ 
		ArrayList<String> pf_value = new ArrayList<String>(); 
		if (!input.equals("1")){ 
			String lag_lead_value = input.substring(0, input.length() - 1); 
			String lag_lead = input.substring(input.length() - 1); 
			pf_value.add(lag_lead_value); pf_value.add(lag_lead); 
		}else{ 
			pf_value.add(input); 
			pf_value.add("L"); 
		} 
		return pf_value;
	}





	public String getPhaseValue2(String PF_Value,String Lag_Lead){
		String phase = "0.0";
		float f_PF_Value = Float.parseFloat(PF_Value);

		if(f_PF_Value== 1.0f){
			phase =  "1";
		}
		else{
			phase =  PF_Value + Lag_Lead;
		}

		ApplicationLauncher.logger.info("getPhaseValue2: phase" + phase);
		return phase;
	}

	public void SaveToDB(){
		ApplicationLauncher.logger.info("SaveToDB: Entry");
		//String freq = ref_txt_frequency.getText();
		String targetCommand = ref_txtSendCommandData.getText();
		String targetCommandTerminator = ref_txtSendCommandTerminator.getText();
		boolean targetCommandTerminatorInHexMode = false;//"N";
		if(ref_chkBxCmdTerminatorInHexMode.isSelected()) {
			targetCommandTerminatorInHexMode = true;//"Y";
		}

		boolean responseTerminatorInHexMode = false;//"N";
		if(ref_chkBxResponseTerminatorInHexMode.isSelected()) {
			responseTerminatorInHexMode = true;//"Y";
		}

		boolean writeSerialNoToDut = false;//"N";		
		if(ref_chkBxWriteSerialNoToDut.isSelected()) {
			writeSerialNoToDut = true;//"Y";
		}

		boolean readSerialNoFromDut = false;//"N";

		if(ref_chkBxReadSerialNoFromDut.isSelected()) {
			readSerialNoFromDut = true;// "Y";
		}

		int haltTimeInSec = 5;//ref_txtHaltTimeInSec.getText(); 

		try {
			haltTimeInSec = Integer.parseInt(ref_txtHaltTimeInSec.getText());
		}catch(Exception e){
			e.printStackTrace();
			ApplicationLauncher.logger.error("dutCommamnd: SaveToDB : haltTimeInSec: Exception:" + e.getMessage());
		}

		int totalDutExecutionTimeInSec = 60;// ref_txtTotalExecutionTimeInSec.getText();

		try {
			totalDutExecutionTimeInSec = Integer.parseInt(ref_txtTotalExecutionTimeInSec.getText());
		}catch(Exception e){
			e.printStackTrace();
			ApplicationLauncher.logger.error("dutCommamnd: SaveToDB : haltTimeInSec: Exception:" + e.getMessage());
		}
		String setSerialNoSourceType = ref_cmbBxSetSerialNoSource.getSelectionModel().getSelectedItem().toString();
		/*		String writeSerialNoEnabled = "N";
		String readSerialNoEnabled = "N";
		String setSerialNoSourceType = ref_cmbBxSetSerialNoSource.getSelectionModel().getSelectedItem().toString();

		if(ref_chkBxWriteSerialNoToDut.isSelected()) {
			writeSerialNoEnabled = "Y";
		}

		if(ref_chkBxReadSerialNoFromDut.isSelected()) {
			readSerialNoEnabled = "Y";
		}*/

		String responseTerminator = ref_txtResponseTerminator.getText();
		//String emax = ref_txt_emax.getText();
		//String time = ref_txt_time_duration.getText();
		String responseExpectedData = ref_txtResponseExpectedData.getText();
		int responseAsciiLength = 5;//String.valueOf(5);/// presently not used

		try {
			responseAsciiLength = 5;//Integer.parseInt(ref_txtResponseTimeoutInSec.getText());
		}catch(Exception e){
			e.printStackTrace();
			ApplicationLauncher.logger.error("dutCommamnd: SaveToDB : responseAsciiLength: Exception:" + e.getMessage());
		}
		//String average  = String .valueOf(ConstantApp.PULSE_AVERAGE_MIN_VALUE);
		//String skip_readings = ref_txt_skip_readings.getText();
		//String test_run_type = ref_comBox_test_run_type.getSelectionModel().getSelectedItem();
		String project_name = mCurrentNode.getProjectName();
		String test_type = mCurrentNode.getType().toString();
		String aliasID = txtAlias_ID.getText();
		String positionID = Integer.toString(mCurrentNode.getPositionId());
		String testcasename = ref_txt_testcasename.getText();
		String summary_display_tp_name = "";
		boolean targetCommandInHexMode = false;//"N";
		boolean responseMandatory = false;//"N";
		int responseTimeOutInSec = 10;
		try {
			responseTimeOutInSec = Integer.parseInt(ref_txtResponseTimeoutInSec.getText());
		}catch(Exception e){
			e.printStackTrace();
			ApplicationLauncher.logger.error("dutCommamnd: SaveToDB : responseTimeOutInSec: Exception:" + e.getMessage());
		}
		if (ref_chkBoxResponseMandatory.isSelected()){
			responseMandatory= true;//"Y";
		}

		if (ref_chkBxCmdInHexMode.isSelected()){
			targetCommandInHexMode= true;//"Y";
		}

		/*		if(test_run_type.equals(ConstantApp.TESTPOINT_RUNTYPE_TIMEBASED)){
			expectedResponseData ="0";
			skip_readings ="0";

		}
		else if(test_run_type.equals(ConstantApp.TESTPOINT_RUNTYPE_PULSEBASED)){
			time="2";

		}*/

		if(!testcasename.isEmpty()){
			if(!(testcasename.contains("_"))){
				//if(!(testcasename.contains("-"))){
				summary_display_tp_name = testcasename+ "_" + aliasID;

				/*				}else{
					saveFailedReason = "hypen not allowed in Test name field";
					ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point save failed : Hypen not allowed:"+ saveFailedReason);
					ApplicationLauncher.InformUser("Saved", "Test point save failed, " + saveFailedReason ,AlertType.ERROR);
					return;
				}*/
			}else{
				saveFailedReason = "Underscore not allowed in Test name field";
				ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point save failed : Underscore not allowed:"+ saveFailedReason);
				ApplicationLauncher.InformUser("Saved", "Test point save failed, " + saveFailedReason ,AlertType.ERROR);
				return;
			}
		}
		else{
			summary_display_tp_name = ref_txtTestType.getText()+ "_" + aliasID;
		}
		boolean status = true;//Validate_AllPhaseParams(u1,u2,u3,i1,i2,i3,ph1,ph2,ph3,freq);
		if(status){
			//if(GuiUtils.FormatErrorInput(targetCommand)!=null){
			//if(GuiUtils.FormatErrorInput(emax)!=null){
			//if(GuiUtils.is_number(expectedResponseData)){
			//if(GuiUtils.is_number(time)){
			//if(GuiUtils.is_number(skip_readings)){
			ProjectController.removeTestPoint(mCurrentNode.getType().toString(), aliasID);
			/*MySQL_Controller.sp_add_project_components(project_name,
										summary_display_tp_name,test_type, aliasID, positionID,
										time , "", "", "","","","", 
										emin, emax, pulses,skip_readings,"",test_run_type, "","","",
										"","","",
										u1,u2,u3,i1,i2,i3,ph1,ph2,ph3,freq,average);*/


			/*MySQL_Controller.sp_add_dut_commands(project_name,
										summary_display_tp_name,test_type, aliasID, positionID,
										//targetCommand,expectedResponseData,responseMandatory,commandInHexMode);
										targetCommand,targetCommandInHexMode, 
										targetCommandTerminator,targetCommandTerminatorInHexMode,
										responseMandatory,responseExpectedData,
										responseTerminator,responseTerminatorInHexMode,
										responseTimeOutInSec,responseAsciiLength,
										haltTimeInSec, totalDutExecutionTimeInSec,
										writeSerialNoToDut,readSerialNoFromDut,
										setSerialNoSourceType

										);*/

			DutCommand dutCommand = new DutCommand();
			dutCommand.setProjectName(project_name);
			dutCommand.setTestCaseName(summary_display_tp_name);
			dutCommand.setTestType(test_type);
			dutCommand.setTestAliasId(aliasID);
			dutCommand.setTestPositionId(positionID);
			dutCommand.setTargetCmd(targetCommand);
			dutCommand.setTargetCmdInHex(targetCommandInHexMode);
			dutCommand.setTargetCmdTerminator(targetCommandTerminator);
			dutCommand.setTargetCmdTerminatorInHex(targetCommandTerminatorInHexMode);
			dutCommand.setResponseMandatory(responseMandatory);
			dutCommand.setResponseExpectedData(responseExpectedData);
			dutCommand.setResponseTerminator(responseTerminator);
			dutCommand.setResponseTerminatorInHex(responseTerminatorInHexMode);
			dutCommand.setResponseTimeOutInSec(responseTimeOutInSec);
			dutCommand.setResponseAsciiLength(responseAsciiLength);
			dutCommand.setHaltTimeInSec(haltTimeInSec);
			dutCommand.setTotalDutExecutionTimeInSec(totalDutExecutionTimeInSec);
			dutCommand.setWriteSerialNoToDut(writeSerialNoToDut);
			dutCommand.setReadSerialNoFromDut(readSerialNoFromDut);
			dutCommand.setSerialNoSourceType(setSerialNoSourceType);
			
			/*if(getLoadedDutCommandId()!=0) {
				//dutCommand.setId(getLoadedDutCommandId());
			}*/
			DeviceDataManagerController.getDutCommandService().saveToDb(dutCommand);

			ProjectController.UpdateNewTestPointSummaryDataToDB(mCurrentNode.getProjectName(), 
					summary_display_tp_name , mCurrentNode.getType().toString(),  
					aliasID);
			ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point saved successfully");
			ApplicationLauncher.InformUser("Saved", "Test point saved successfully",AlertType.INFORMATION);
			/*}
							else{
								ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point save failed : skip reading is not a valid number");
								ApplicationLauncher.InformUser("Saved", "Test point save failed,  skip reading is not a valid number",AlertType.ERROR);
							}*/
			/*					}
						else{
							ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point save failed : time is not a valid number");
							ApplicationLauncher.InformUser("Saved", "Test point save failed,  time is not a valid number",AlertType.ERROR);
						}*/
			/*					}
					else{
						ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point save failed : pulse is not a valid number");
						ApplicationLauncher.InformUser("Saved", "Test point save failed,  pulse is not a valid number",AlertType.ERROR);
					}*/
			/*				}
				else{
					ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point save failed : Error max is not a valid format");
					ApplicationLauncher.InformUser("Saved", "Test point save failed,  Error max is not a valid format",AlertType.ERROR);
				}*/
			/*			}
			else{
				ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point save failed : Error min is not a valid format");
				ApplicationLauncher.InformUser("Saved", "Test point save failed,  Error min is not a valid format",AlertType.ERROR);
			}*/
		}
		else{
			ApplicationLauncher.logger.info ("PropertyDutCommandController: SaveToDB: Test point save failed : RYB data validation failed:"+ saveFailedReason);
			ApplicationLauncher.InformUser("Saved", "Test point save failed,  RYB data validation failed:" + saveFailedReason ,AlertType.ERROR);
		}

	}

	public boolean Validate_AllPhaseParams(String u1, String u2, String u3, 
			String i1, String i2, String i3, String ph1,
			String ph2, String ph3, String freq) {
		boolean validation_status;
		ApplicationLauncher.logger.info("Validate_AllPhaseParams: Entry"  );
		String metertype = ProjectController.getProjectEM_ModelType();
		validation_status = Phase_Based_Check(u1, i1, ph1);
		if(validation_status){
			ApplicationLauncher.logger.info("Validate_AllPhaseParams: 1 : " + validation_status );
			if(metertype.contains(ConstantApp.METERTYPE_SINGLEPHASE)){
				validation_status = GuiUtils.Validate_frequency(freq);
				if(!validation_status){
					saveFailedReason = "Single phase-frequency failed" ;
				}

			}else{
				validation_status = Phase_Based_Check(u2, i2, ph2);
				if(validation_status){
					ApplicationLauncher.logger.info("Validate_AllPhaseParams: 2 : " + validation_status );
					validation_status = Phase_Based_Check(u3, i3, ph3);
					if(validation_status){
						ApplicationLauncher.logger.info("Validate_AllPhaseParams: 3 : " + validation_status );
						validation_status = GuiUtils.Validate_frequency(freq);
						if(!validation_status){
							saveFailedReason = "Three phase-frequency failed" ;
						}
					}else{
						saveFailedReason = "B-Phase: "+ saveFailedReason ;
					}
				}else{
					saveFailedReason = "Y-Phase: "+ saveFailedReason ;
				}
			}
		}else{
			saveFailedReason = "R-Phase: "+ saveFailedReason ;
		}
		return validation_status;
	}

	public boolean Phase_Based_Check(String volt, String current, String phasedegree){
		boolean validation_status = false;
		if(Float.parseFloat(volt) == 0.0f){
			validation_status = true;
		} else{
			validation_status = GuiUtils.Validate_voltage(volt);
		}
		ApplicationLauncher.logger.info("Phase_Based_Check: voltage: " + validation_status );
		if(validation_status){
			if(Float.parseFloat(current) == 0.0f){
				validation_status = true;
			}else{
				validation_status = GuiUtils.Validate_current(current);
			}
			ApplicationLauncher.logger.info("Phase_Based_Check: current: " + validation_status );
			if(validation_status){
				validation_status = GuiUtils.Validate_PhaseLagLead(phasedegree);
				ApplicationLauncher.logger.info("Phase_Based_Check: phasedegree: " + validation_status );
				if(!validation_status){
					saveFailedReason = "input phasedegree validation failed due to invalid float value or not with in configured acceptable limit or empty";

				}
			}else{
				saveFailedReason = "input current parameter validation failed due to invalid float value or not with in configured acceptable limit or empty";
			}
		}else{
			saveFailedReason = "Voltage validation failed due to invalid float value or not with in configured acceptable limit or empty";
		}
		return validation_status;
	}

	/*	public void InformUser(String title, String info,AlertType Alert_type){
		TextBoxDialog TextBoxDialogobj = new TextBoxDialog();
		TextBoxDialogobj.TriggerUserInfoPlatFormLater(title, info, Alert_type);
	}*/

	public void SaveCustomRatingTrigger(){

		ApplicationLauncher.logger.info("SaveCustomRatingTrigger Invoked:");

		SaveDataTimer = new Timer();
		SaveDataTimer.schedule(new SaveCustomRatingTask(),100);

	}

	public boolean IsFieldEmpty(){
		ProjectController.getProjectEM_ModelType();
		if (!ref_txt_testcasename.getText().isEmpty()){
			if (!ref_txtSendCommandData.getText().isEmpty()){
				//if (!ref_txt_emax.getText().isEmpty()){
				/*if (!ref_txt_frequency.getText().isEmpty()){

					}else{
						ApplicationLauncher.logger.info ("IsFieldEmpty: <Frequency> field is empty Prompt:");
						ApplicationLauncher.InformUser("Empty Field", "<Frequency> field is empty",AlertType.ERROR);
						return true;
					}*/
				/*}else{
					ApplicationLauncher.logger.info ("IsFieldEmpty: <Emax> field is empty Prompt:");
					ApplicationLauncher.InformUser("Empty Field", "<Emax> field is empty",AlertType.ERROR);
					return true;
				}*/
			}else{

				ApplicationLauncher.logger.info ("IsFieldEmpty: <Emin> field is empty Prompt:");
				ApplicationLauncher.InformUser("Empty Field", "<Emin> field is empty",AlertType.ERROR);
				return true;

			}
		}else{

			ApplicationLauncher.logger.info ("IsFieldEmpty: <Test Name> field is empty Prompt:");
			ApplicationLauncher.InformUser("Empty Field", "<Test Name> field is empty",AlertType.ERROR);
			return true;
		}

		return false;
	}

	class SaveCustomRatingTask extends TimerTask {
		public void run() {
			if (!IsFieldEmpty()){
				ApplicationLauncher.setCursor(Cursor.WAIT);
				mCurrentNode.NewNode(false);
				String currentProject = mCurrentNode.getProjectName();
				String currentTestType = mCurrentNode.getType().toString();
				String currentAliasId = mCurrentNode.getAliasId();
				MySQL_Controller.sp_delete_project_components(currentProject, currentTestType, currentAliasId);
				MySQL_Controller.sp_delete_summary_data(currentProject, currentTestType, currentAliasId);
				//if(currentTestType.equals("InfluenceHarmonic")){
				if(currentTestType.equals(TestProfileType.InfluenceHarmonic.toString())){
					MySQL_Controller.sp_delete_harmonic_data(currentProject, currentTestType, currentAliasId);
				}
				//ProjectController.refreshSummaryPaneData("", currentAliasId);
				ProjectController.RefreshSummaryDataFromDB();
				SaveToDB();
				ProjectController.SaveProject();
				ProjectController.LoadSummaryDataToGUI();
				ApplicationLauncher.setCursor(Cursor.DEFAULT);
			}
			SaveDataTimer.cancel();
		}
	}

	/*public static long getLoadedDutCommandId() {
		return loadedDutCommandId;
	}


	public static void setLoadedDutCommandId(long loadedDutCommandId) {
		PropertyDutCommandController.loadedDutCommandId = loadedDutCommandId;
	}*/


}

