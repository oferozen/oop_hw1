package homework1;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 * A JDailog GUI for choosing a GeoSegemnt and adding it to the route shown
 * by RoutDirectionGUI.
 * <p>
 * A figure showing this GUI can be found in homework assignment #1.
 */
public class GeoSegmentsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	// the RouteDirectionsGUI that this JDialog was opened from
	private RouteFormatterGUI parent;
	
	// a control contained in this 
	private JList<GeoSegment> lstSegments;
	
	/**
	 * Creates a new GeoSegmentsDialog JDialog.
	 * @effects Creates a new GeoSegmentsDialog JDialog with owner-frame
	 * 			owner and parent pnlParent
	 */
	public GeoSegmentsDialog(Frame owner, RouteFormatterGUI pnlParent) {
		// create a modal JDialog with the an owner Frame (a modal window
		// in one that doesn't allow other windows to be active at the
		// same time).
		super(owner, "Please choose a GeoSegment", true);
		
		this.parent = pnlParent;
		
		// TODO Write the body of this method
		
		lstSegments = new JList<>(new DefaultListModel<GeoSegment>());
		lstSegments.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrlSegments = new JScrollPane(lstSegments);
		scrlSegments.setPreferredSize(new Dimension(350, 150));
		
		JLabel lblSegments = new JLabel("GeoSegments:");
		lblSegments.setLabelFor(lstSegments);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);
		

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.addSegment(lstSegments.getSelectedValue());
				lstSegments.clearSelection();
				setVisible(false);
			}
		});
		

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lstSegments.clearSelection();
				setVisible(false);
			}
		});

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(20,20,0,0);
		gridbag.setConstraints(lblSegments, c);
		this.add(lblSegments);
		
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 5;
		c.insets = new Insets(0,20,20,20);
		gridbag.setConstraints(scrlSegments, c);
		this.add(scrlSegments);
		
		c.gridx = 1;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,20,20,400);
		c.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(btnAdd, c);
		this.add(btnAdd);
		
		
		c.gridx = 1;
		c.gridy = 10;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,400,20,20);
		c.anchor = GridBagConstraints.SOUTH;
		gridbag.setConstraints(btnCancel, c);
		this.add(btnCancel);
		
		
		DefaultListModel<GeoSegment> model =
				(DefaultListModel<GeoSegment>)(this.lstSegments.getModel());
		
		for(int i = 0 ; i < ExampleGeoSegments.segments.length ; i++) {
			model.addElement(ExampleGeoSegments.segments[i]);
		}
		
	}
}
