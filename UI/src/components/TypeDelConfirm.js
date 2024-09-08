import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Button from "@mui/material/Button";

export default function DeleteConfirmationDialog({ open, handleClose, handleConfirm, typeId }) {
    return (
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>You are deleting a Type</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Are you sure you want to delete Type with ID: {typeId}? <br/>
            This will also delete all assets of this type <br/>

            This cannot be undone!
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleConfirm} autoFocus>
            Delete
          </Button>
          <Button onClick={handleClose}>Cancel</Button>
        </DialogActions>
      </Dialog>
    );
  }