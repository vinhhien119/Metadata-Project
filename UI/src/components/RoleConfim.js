import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import Button from "@mui/material/Button";

export default function RoleConfirmationDialog({ open, handleClose, handleConfirm, userData }) {
    return (
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>You are changing the role</DialogTitle>
        <DialogContent>
          <DialogContentText>
            You are change the role of user: {userData.updatingUsername}? <br/>
            New role will be {userData.updatingRole}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleConfirm} autoFocus>
            Confirm
          </Button>
          <Button onClick={handleClose}>Cancel</Button>
        </DialogActions>
      </Dialog>
    );
  }