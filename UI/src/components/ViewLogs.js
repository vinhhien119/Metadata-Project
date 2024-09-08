import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Title from './Title';
import * as LogAPI from '../utility/LogAPI';

export default function ViewLogs() {
  React.useEffect(() => {
    const getLogs = async () => {
      const res = await LogAPI.getAll();
      console.log(res)
      setLogs(res)
    };

    getLogs();
  }, []);

  const [logs, setLogs] = React.useState([])


  return (
    <React.Fragment>
      <Title>Action Log</Title>
      <Table size="small">
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Asset ID</TableCell>
            <TableCell>Type ID</TableCell>
            <TableCell>Action</TableCell>
            <TableCell>Timestamp</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {logs.map((l) => (
            <TableRow key={l.id}>
              <TableCell>{l.id}</TableCell>
              <TableCell>{l.assetId}</TableCell>
              <TableCell>{l.typeId}</TableCell>
              <TableCell>{l.action}</TableCell>
              <TableCell>{l.timestamp}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </React.Fragment>
  );
}