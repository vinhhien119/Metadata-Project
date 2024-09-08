import * as React from "react";
import { useState, useEffect } from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import CancelIcon from "@mui/icons-material/Cancel";
import SaveIcon from "@mui/icons-material/Save";
import Stack from "@mui/material/Stack";
import Grid from "@mui/material/Grid";
import * as TypeAPI from "../utility/TypeAPI"
import { useNavigate } from "react-router-dom"; 



export default function FormPropsTextFields() {
  //state variables for save and cancel buttons
  const [save, setSave] = useState("Save");
  const [cancel, setCancel] = useState("Cancel");

  //state variables for form fields
  const [typeName, setTypeName] = useState("");
  const [customAttribute1, setCustomAttribute1] = useState("");
  const [customAttribute2, setCustomAttribute2] = useState("");
  const [customAttribute3, setCustomAttribute3] = useState("");
  const [customAttribute4, setCustomAttribute4] = useState("");
  const [alertMessage, setAlertMessage] = useState("");
  const navigate = useNavigate(); 

  
  //useEffect hook to handle changes after save button is clicked
  useEffect(() => {
    if (save === "Saved") {
      const timer = setTimeout(() => {
        setSave("Save");
      }, 2000); // Changes back to "Saved" after 2 seconds
      return () => clearTimeout(timer);
    }
  }, [save]);

  //useEffect hook to handle changes after cancel button is clicked
  useEffect(() => {
    if (cancel === "Cancelled") {
      const timer = setTimeout(() => {
        document.getElementById("cancel-button").style.backgroundColor = "white";
        document.getElementById("cancel-button").style.color = "blue";
        setCancel("Cancel");
      }, 1500); // Changes back to "Cancel" after 1.5 seconds
      return () => clearTimeout(timer);
    }

  }, [cancel]);

  //function to handle changes when save button is clicked
  const handleSaveButtonClick = async (event) => {
    event.preventDefault();
    
    let newAlertMessage = "";
    if (!typeName || !customAttribute1) {
      newAlertMessage = "Please fill in all mandatory fields";
    }
     
    if (newAlertMessage) {
      setAlertMessage(newAlertMessage);
      return; // Return early if invalid input
    }
    setAlertMessage("");
    const compType = await TypeAPI.getTypeExists(typeName);
    if (!compType) {
      try {
        const response = await TypeAPI.addType({
          typeName,
          customAttribute1,
          customAttribute2,
          customAttribute3,
          customAttribute4
        });
  
        if (!response.ok) {
          throw new Error('Failed to add type');
        }
        resetValue();
        setSave("Saved");
        console.log('Type added successfully');
        const types = await TypeAPI.getAll();
        const addedType = types.find(type => type.typeName === typeName);
        if (addedType) {
          navigate(`/type/open/${addedType.id}`);
        }
      } catch (error) {
        console.error('Error adding type:', error);
      }
    } else {
      alert("Type name already exists");
    }
  };

  const resetValue = () => {
    setTypeName("");
    setCustomAttribute1("");
    setCustomAttribute2("");
    setCustomAttribute3("");
    setCustomAttribute4("");
  }

  //function to handle changes when cancel button is clicked
  const handleCancelButtonClick = () => {
    const cancelButtonStyle = document.getElementById("cancel-button").style;
    cancelButtonStyle.backgroundColor = "blue";
    cancelButtonStyle.color = "red";
    setCancel("Cancelled");
    
    resetValue()
  };

  return (
    <Box
      component="form"
      sx={{
        "& .MuiTextField-root": { m: 1, width: "40ch" },
        background: "white",
        width: "100%",
        maxWidth: "100%",
        margin: 0,
      }}
      noValidate
      autoComplete="off"
    >
      <Grid
        container
        spacing={3}
        direction="row"
        justifyContent="center"
        alignItems="center"
        sx={{
          paddingBottom: 5,
        }}
      >
        <Grid item xs={5}>
          <Grid container spacing={3} direction="column">
            <Grid item>
            {/* Alert for mandatory boxes */}
            {alertMessage && (
                <div style={{ color: "red", marginBottom: "10px" }}>
                    {alertMessage}
                </div>
             )}
    
              <TextField
                id="outlined-textarea"
                label="Type Name"
                placeholder="Document"
                multiline
                required
                value={typeName}
                onChange={(e) => setTypeName(e.target.value)}
              />
            </Grid>
          </Grid>
        </Grid>
        <Grid item xs={5}>
          <TextField
            id="outlined-textarea"
            label="Custom Attribute Name 1"
            placeholder="Author"
            multiline
            required
            value={customAttribute1}
            onChange={(e) => setCustomAttribute1(e.target.value)}
          />
        </Grid>
        <Grid item xs={5}>
          <TextField
            id="outlined-textarea"
            label="Custom Attribute Name 2"
            placeholder="Security Rating"
            multiline
            value={customAttribute2}
            onChange={(e) => setCustomAttribute2(e.target.value)}
          />
        </Grid>
        <Grid item xs={5}>
          <TextField
            id="outlined-textarea"
            label="Custom Attribute Name 3"
            placeholder="Location"
            multiline
            value={customAttribute3}
            onChange={(e) => setCustomAttribute3(e.target.value)}
          />
        </Grid>
        <Grid item xs={5}>
          <TextField
            id="outlined-textarea"
            label="Custom Attribute Name 4"
            placeholder="Project Name"
            multiline
            value={customAttribute4}
            onChange={(e) => setCustomAttribute4(e.target.value)}
          />
        </Grid>
      </Grid>
    
      <Stack direction="row" spacing={2}>
        <Button
          variant="contained"
          endIcon={<SaveIcon />}
          style={{ background: "black" }}
          onClick={handleSaveButtonClick}
        >
          {save}
        </Button>
        <Button
          id="cancel-button"
          variant="outlined"
          startIcon={<CancelIcon />}
          onClick={handleCancelButtonClick}
        >
          {cancel}
        </Button>
      </Stack>
    </Box>

  );
}
