import React, { useState } from 'react'
import * as UserAPI from "../utility/UserAPI";
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
import DeleteConfirmationDialog from './UserDelConfirm';
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import ToggleButton from '@mui/material/ToggleButton';
import ToggleButtonGroup from '@mui/material/ToggleButtonGroup';
import RoleConfirmationDialog from './RoleConfim';


function App(props) {

  React.useEffect(() => {
    const getUsers = async () => {
      const res = await UserAPI.getAll();
      setUsers(res);
      setOriginalUsers(res);
      if (res.length > 0) {
        const userAttributes = Object.keys(res[0]);
        setUserAttributes(userAttributes);
        setSelectedUserAttribute("name");
      }
    };
    getUsers();
  }, []);
  

  const [users, setUsers] = React.useState([]);
  const [updatingUsername, setUpdatingUsername] = React.useState("");
  const [updatingRole, setUpdatingRole] = React.useState("");
  const [openRoleDialog, setOpenRoleDialog] = React.useState(false);
  const [originalUsers, setOriginalUsers] = useState([]);
	const [searchVal, setSearchVal] = useState("");
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [openDeleteDialog, setOpenDeleteDialog] = React.useState(false);
  const [deletingUserId, setDeletingUserId] = React.useState(null);
  const [userAttributes, setUserAttributes] = useState([]);
  const [selectedUserAttribute, setSelectedUserAttribute] = useState("");
  const filteredUserAttributes = userAttributes.filter(attributeName => attributeName !== 'password');
  const [role, setRole] = React.useState('READER');
  const { username } = props;


  // Function to handle type selection from dropdown
  const handleUserAttributeChange = (event) => {
    const attributeName = event.target.value;
    setSelectedUserAttribute(attributeName);
  };

  const handleDelete = (id) => {
    setDeletingUserId(id);
    setOpenDeleteDialog(true);
  };

  const handleDeleteUser = async (id) => {
    try {
      console.log(id);
      const response = await UserAPI.deleteById(id);
      setUsers((users) => users.filter((u) => u.id !== id));
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

  // Function to handle live search as the user users
  const handleSearchChange = (event) => {
    const value = event.target.value;
    setSearchVal(value);
    // Filter the original types based on the search value
    const filteredUsers = originalUsers.filter((user) => {
      const stringQuery = String(user[selectedUserAttribute]);
      return stringQuery.toLowerCase().includes(value.toLowerCase());
    });
    // Update the displayed types with the filtered results
    setUsers(filteredUsers);
  };


  const handleRoleClick = (username, role) => {
    setUpdatingUsername(username);
    setUpdatingRole(role)
    setOpenRoleDialog(true)
  };

  const handleRoleChange = async () => {
    const res = await UserAPI.updateRole({
      updatingUsername,
      updatingRole
    });

    if(res.ok) {
      const updatedUsers = await UserAPI.getAll();
      setUsers(updatedUsers);
    }
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
          label="User Attribute"
          value={selectedUserAttribute}
          onChange={handleUserAttributeChange}
        >
          {filteredUserAttributes.map((attributeName) => (
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
          <TableCell>Name</TableCell>
          <TableCell>Username</TableCell>
          <TableCell align="right">User Permissions</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {(rowsPerPage > 0
          ? users.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
          : users
        ).map((u) => (
          <TableRow key={u.id}>
            <TableCell>{u.id}</TableCell>
            <TableCell>{`${u.firstname} ${u.lastname}`}</TableCell>
            <TableCell>{u.username}</TableCell>
            <TableCell align="right">
              <ToggleButtonGroup
                color="primary"
                value={u.role}
                exclusive
                onChange={(event, value) => handleRoleClick(u.username, value)}
                disabled={u.username === username}
              >
                <ToggleButton value="READER" data-userid={u.id}>Reader</ToggleButton>
                <ToggleButton value="USER" data-userid={u.id}>User</ToggleButton>
                <ToggleButton value="ADMIN" data-userid={u.id}>Admin</ToggleButton>
              </ToggleButtonGroup>
              </TableCell>
            <TableCell align="right">
              <IconButton
                className={styles.link}
                onClick={() => handleDelete(u.id)}
              >
                <DeleteIcon />
              </IconButton>
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
    <TablePagination
      rowsPerPageOptions={[5, 10, 25, { label: "All", value: -1 }]}
      component="div"
      count={users.length}
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
    <DeleteConfirmationDialog
      open={openDeleteDialog}
      handleClose={() => setOpenDeleteDialog(false)}
      handleConfirm={() => {
        setOpenDeleteDialog(false);
        handleDeleteUser(deletingUserId);
      }}
      typeId={deletingUserId}
    />
    <RoleConfirmationDialog 
    open ={openRoleDialog}
    handleClose = {() => setOpenRoleDialog(false)}
    handleConfirm={() => {
      setOpenRoleDialog(false);
      handleRoleChange();
    }
    }
    userData ={{updatingUsername, updatingRole}}/>
    </React.Fragment>

	);
}

export default App;
