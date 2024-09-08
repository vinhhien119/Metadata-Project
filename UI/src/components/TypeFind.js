import React, { useState } from 'react'
import * as TypeAPI from "../utility/TypeAPI";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import { Link } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import VisibilityIcon from '@mui/icons-material/Visibility';
import styles from "../style/listItems.module.css";
import { IconButton, TablePagination } from "@mui/material";
import Stack from "@mui/material/Stack";
import Button from "@mui/material/Button";
import UndoIcon from "@mui/icons-material/Undo";
import DeleteConfirmationDialog from './TypeDelConfirm';
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import EditConfirmationDialog from './TypeEditConfirm'; 

function App(props) {

  React.useEffect(() => {
    const getTypes = async () => {
      const res = await TypeAPI.getAll();
      if (res && res.length > 0) {
	      setTypes(res);
	      setOriginalTypes(res);
	      const typeAttributes = Object.keys(res[0]);
	      setTypeAttributes(typeAttributes);
	      setSelectedTypeAttribute("typeName");
	  } else {
		  setTypes([]);
	      setOriginalTypes([]);
	      setTypeAttributes([]);
	      setSelectedTypeAttribute("");
	  }
    };
    getTypes();
  }, []);

  const [types, setTypes] = React.useState([])
  const [originalTypes, setOriginalTypes] = useState([]);
  const [searchVal, setSearchVal] = useState("");
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [openDeleteDialog, setOpenDeleteDialog] = React.useState(false);
  const [deletingTypeId, setDeletingTypeId] = React.useState(null);
  const [typeAttributes, setTypeAttributes] = useState([]);
  const [selectedTypeAttribute, setSelectedTypeAttribute] = useState("");
  const { role } = props;

  const [openEditConfirmation, setOpenEditConfirmation] = React.useState(false); 
  const [editingTypeId, setEditingTypeId] = React.useState(null);

  const handleEditConfirmation = (id) => {
    setEditingTypeId(id);
    setOpenEditConfirmation(true);
  };

  const handleEditType = () => {
    setOpenEditConfirmation(false);
    // Navigate to the oter page
  };


  // Function to handle type selection from dropdown
  const handleTypeAttributeChange = (event) => {
    const attributeName = event.target.value;
    setSelectedTypeAttribute(attributeName);
  };

  const handleDelete = (id) => {
    setDeletingTypeId(id);
    setOpenDeleteDialog(true);
  };

  const handleDeleteType = async (id) => {
    try {
      console.log(id);
      const response = await TypeAPI.deleteById(id);
      setTypes((types) => types.filter((t) => t.id !== id));
      if (response.status === 200) {
        console.log("Deleted");
      } else {
        console.error("Failed to delete");
      }
    } catch (error) {
      console.log(error);
    }
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  // Function to handle live search as the user types
  const handleSearchChange = (event) => {
    const value = event.target.value;
    setSearchVal(value);
    // Filter the original types based on the search value
    const filteredTypes = originalTypes.filter((type) => {
      const stringQuery = String(type[selectedTypeAttribute]);
      return stringQuery.toLowerCase().includes(value.toLowerCase());
    });
    // Update the displayed types with the filtered results
    setTypes(filteredTypes);
  };

	return (
    <React.Fragment>
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
      <Grid item xs={6}
      alignItems="center"
      >
        <TextField
          id="outlined-select-currency"
          select
          label="Type Attribute"
          value={selectedTypeAttribute}
          onChange={handleTypeAttributeChange}
        >
          {typeAttributes.map((attributeName) => (
            <MenuItem key={attributeName} value={attributeName}>
              {attributeName}
            </MenuItem>
          ))}
        </TextField>
      </Grid>  
      <Grid item xs={6}
      alignItems="center"
      >
        <TextField
          id="outlined-textarea"
          placeholder="Enter a search term"
          multiline
          value={searchVal}
          onChange={handleSearchChange}
        />
      </Grid>
      </Grid>

      </Box>

    
    <Table size="small">
      <TableHead>
        <TableRow>
          <TableCell>ID</TableCell>
          <TableCell>Type Name</TableCell>
          <TableCell>Custom Attribute 1</TableCell>
          <TableCell>Custom Attribute 2</TableCell>
          <TableCell>Custom Attribute 3</TableCell>
          <TableCell align="right">Custom Attribute 4</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {(rowsPerPage > 0
          ? types.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
          : types
        ).map((t) => (
          <TableRow key={t.id}>
            <TableCell>{t.id}</TableCell>
            <TableCell>{t.typeName}</TableCell>
            <TableCell>{t.customAttribute1}</TableCell>
            <TableCell>{t.customAttribute2}</TableCell>
            <TableCell>{t.customAttribute3}</TableCell>
            <TableCell align="right">{t.customAttribute4}</TableCell>
            <TableCell align="right">
            <IconButton className={styles.link}>
            <Link to={`/type/open/${t.id}`} className={styles.link}>
                <VisibilityIcon />
            </Link>
            </IconButton>
            {role === 'ADMIN' && (
            <>
              <IconButton className={styles.link}>
                <EditIcon onClick={() => handleEditConfirmation(t.id)} />
              </IconButton>
              <IconButton
                className={styles.link}
                onClick={() => handleDelete(t.id)}
              >
                <DeleteIcon />
              </IconButton>
            </>
            )}
            </TableCell>
          </TableRow>
        ))}
        {types.length === 0 && (
          <TableRow>
            <TableCell colSpan={6} align="center">
              No types found.
            </TableCell>
          </TableRow>
         )}
      </TableBody>
    </Table>
    <TablePagination
      rowsPerPageOptions={[5, 10, 25, { label: "All", value: -1 }]}
      component="div"
      count={types.length}
      rowsPerPage={rowsPerPage}
      page={page}
      onPageChange={handleChangePage}
      onRowsPerPageChange={handleChangeRowsPerPage}
    />

    <Stack direction="row" spacing={2}>
    <Link to="/welcome">
      <Button id="cancel-button" variant="outlined" endIcon={<UndoIcon />}>
        Back To Dashboard
      </Button>
    </Link>
    </Stack>

    <EditConfirmationDialog
        open={openEditConfirmation}
        handleClose={() => setOpenEditConfirmation(false)}
       
        handleConfirm={() => {
          setOpenEditConfirmation(false);
          handleEditType(editingTypeId);
        }}
        typeId={editingTypeId}
      />

    <DeleteConfirmationDialog
      open={openDeleteDialog}
      handleClose={() => setOpenDeleteDialog(false)}
      handleConfirm={() => {
        setOpenDeleteDialog(false);
        // Call handleDeleteType function to delete the type
        handleDeleteType(deletingTypeId);
      }}
      typeId={deletingTypeId}
    />
    </React.Fragment>

	);
}

export default App;
