import React from 'react';
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Button from "@mui/material/Button";
import { Link } from 'react-router-dom';

export default function EditConfirmationDialog({ open, handleClose,  assetId }) {
  const handleEdit = () => {
    // Navigate to the edit page when the "Edit" button is clicked in the popup
    window.location.href = `/asset/edit/${assetId}`;
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>You are Editing An Asset</DialogTitle>
      <DialogContent>
        <DialogContentText>
          Are you sure you want to Edit asset with ID: {assetId}? <br/>
          <Link to={`/asset/edit/${assetId}`}></Link>
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleEdit} autoFocus>
          Edit
        </Button>
        <Button onClick={handleClose}>Cancel</Button>
      </DialogActions>
    </Dialog>
  );
}
