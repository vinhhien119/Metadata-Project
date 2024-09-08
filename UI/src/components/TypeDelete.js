import * as React from "react";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import Button from "@mui/material/Button";
import CancelIcon from "@mui/icons-material/Cancel";
import SaveIcon from "@mui/icons-material/Save";
import Stack from "@mui/material/Stack";
import Grid from "@mui/material/Grid";


export default function FormPropsTextFields() {
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
        <Grid item xs={6}>
          <TextField
            id="outlined-textarea"
            label="ID To Delete"
            placeholder="3"
            multiline
          />
        </Grid>
      </Grid>

      <Stack direction="row" spacing={2}>
        <Button
          variant="contained"
          endIcon={<SaveIcon />}
          style={{ background: "black" }}
        >
          Submit
        </Button>
        <Button
          variant="outlined"
          startIcon={<CancelIcon />}
        >
          Cancel
        </Button>
      </Stack>
    </Box>
  );
}
