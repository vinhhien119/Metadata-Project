import React, { useState } from 'react'
import SearchIcon from '@mui/icons-material/Search';
import * as AssetAPI from "../utility/AssetAPI";
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
import DeleteConfirmationDialog from './AssetDelConfirm';
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import MenuItem from "@mui/material/MenuItem";
import EditConfirmationDialog from './AssetEditConfirm'; 

function App(props) {

  React.useEffect(() => {
    const getAssets = async () => {
      const res = await AssetAPI.getAll();
      if (res && res.length > 0) {
	      setAssets(res);
	      setOriginalAssets(res);
	      const assetAttributes = Object.keys(res[0]);
	      setAssetAttributes(assetAttributes);
	      setSelectedAssetAttribute("title"); 
	  } else {
		  setAssets([]);
	      setOriginalAssets([]);
	      setAssetAttributes([]);
	      setSelectedAssetAttribute("");  
	  }
    };
    getAssets();
  }, []);

  const [assets, setAssets] = React.useState([])
  const [originalAssets, setOriginalAssets] = useState([]);
	const [searchVal, setSearchVal] = useState("");
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [openDeleteDialog, setOpenDeleteDialog] = React.useState(false);
  const [deletingAssetId, setDeletingAssetId] = React.useState(null);
  const [assetAttributes, setAssetAttributes] = useState([]);
  const [selectedAssetAttribute, setSelectedAssetAttribute] = useState("");

  const [openEditConfirmation, setOpenEditConfirmation] = React.useState(false); 
  const [editingAssetId, setEditingAssetId] = React.useState(null);
  const { role } = props;

  const handleEditConfirmation = (id) => {
    setEditingAssetId(id);
    setOpenEditConfirmation(true);
  };

  const handleEditAsset = () => {
    setOpenEditConfirmation(false);
    // Navigate to the other page
  };

  // Function to handle type selection from dropdown
  const handleAssetAttributeChange = (event) => {
    const attributeName = event.target.value;
    setSelectedAssetAttribute(attributeName);
  };

  const handleDelete = (id) => {
    setDeletingAssetId(id);
    setOpenDeleteDialog(true);
  };

  const handleDeleteAsset = async (id) => {
    try {
      console.log(id);
      const response = await AssetAPI.deleteById(id);
      setAssets((assets) => assets.filter((a) => a.id !== id));
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
    // Filter the original assets based on the search value
    const filteredAssets = originalAssets.filter((asset) => {
      const stringQuery = String(asset[selectedAssetAttribute]);
      return stringQuery.toLowerCase().includes(value.toLowerCase());
    });
    // Update the displayed assets with the filtered results
    setAssets(filteredAssets);
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
          label="Asset Attribute"
          value={selectedAssetAttribute}
          onChange={handleAssetAttributeChange}
        >
          {assetAttributes.map((attributeName) => (
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
          <TableCell>Type</TableCell>
          <TableCell>Link</TableCell>
          <TableCell>Title</TableCell>
          <TableCell>Author</TableCell>
          <TableCell align="right">Actions</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {(rowsPerPage > 0
          ? assets.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
          : assets
        ).map((a) => (
          <TableRow key={a.id}>
            <TableCell>{a.id}</TableCell>
            <TableCell>{a.type}</TableCell>
            <TableCell>{a.link}</TableCell>
            <TableCell>{a.title}</TableCell>
            <TableCell>{a.author}</TableCell>
            <TableCell align="right">
            <IconButton className={styles.link}>
            <Link to={`/asset/open/${a.id}`} className={styles.link}>
                <VisibilityIcon />
            </Link>
            </IconButton>
            {(role === 'ADMIN' || role === 'USER') && (
            <>
              <IconButton className={styles.link}>
                <EditIcon onClick={() => handleEditConfirmation(a.id)} />
              </IconButton>              
              <IconButton
                className={styles.link}
                onClick={() => handleDelete(a.id)}
              >
                <DeleteIcon />
              </IconButton>
            </>
            )}
            </TableCell>
          </TableRow>
        ))}
        {assets.length === 0 && (
          <TableRow>
            <TableCell colSpan={6} align="center">
              No assets found.
            </TableCell>
          </TableRow>
         )}
      </TableBody>
    </Table>
    <TablePagination
      rowsPerPageOptions={[5, 10, 25, { label: "All", value: -1 }]}
      component="div"
      count={assets.length}
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
          handleEditAsset(editingAssetId);
        }}
        assetId = {editingAssetId}
      />

    <DeleteConfirmationDialog
      open={openDeleteDialog}
      handleClose={() => setOpenDeleteDialog(false)}
      handleConfirm={() => {
        setOpenDeleteDialog(false);
        // Call handleDeleteAsset function to delete the asset
        handleDeleteAsset(deletingAssetId);
      }}
      assetId={deletingAssetId}
    />
    </React.Fragment>

	);
}

export default App;
